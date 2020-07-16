package com.github.ser.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.auth.InactiveUserException;
import com.github.ser.exception.auth.PasswordMissingException;
import com.github.ser.exception.auth.UsernameMissingException;
import com.github.ser.model.database.User;
import com.github.ser.model.response.ErrorResponse;
import com.github.ser.service.UserService;
import com.github.ser.util.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Log4j2
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;

        setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter(getUsernameParameter());
        String password = request.getParameter(getPasswordParameter());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {


        String email = (String) authResult.getPrincipal();
        User user = userService.getUserByEmail(email);

        String authToken = jwtTokenUtil.generateTokenForUser(user);

        response.addHeader("Authorization", "Bearer " + authToken);
        response.getWriter().write("{\"authToken\": \"" + authToken + "\"}");
        response.setContentType(MediaType.APPLICATION_JSON.toString());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        super.getRememberMeServices().loginFail(request, response);

        String message = failed.getMessage();
        int errorCode = ErrorCodes.UNEXPECTED.getCode();

        if (failed instanceof PasswordMissingException) {
            errorCode = ErrorCodes.PASSWORD_MISSING.getCode();
        } else if (failed instanceof UsernameMissingException) {
            errorCode = ErrorCodes.USERNAME_MISSING.getCode();
        } else if (failed instanceof InactiveUserException) {
            errorCode = ErrorCodes.INACTIVE_USER.getCode();
        } else if (failed instanceof BadCredentialsException) {
            errorCode = ErrorCodes.CREDENTIALS_INVALID.getCode();
        } else if (failed instanceof UsernameNotFoundException) {
            errorCode = ErrorCodes.CREDENTIALS_INVALID.getCode();
            message = "Credentials invalid";
        }

        response.getWriter().write(new ObjectMapper().writeValueAsString(ErrorResponse
                .builder()
                .message(message)
                .errorCode(errorCode)
                .build()));

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
    }
}