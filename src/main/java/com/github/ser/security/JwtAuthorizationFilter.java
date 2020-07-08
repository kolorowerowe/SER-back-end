package com.github.ser.security;

import com.github.ser.model.database.User;
import com.github.ser.service.UserService;
import com.github.ser.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        super(authManager);
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        if (token != null) {
            // parse the token.
            String email = jwtTokenUtil.getEmailFromToken(token);
            User user = userService.getUserByEmail(email);
            if (email != null) {
                return new UsernamePasswordAuthenticationToken(email, null, Collections.singleton(user.getRole()));
            }
            return null;
        }
        return null;
    }
}