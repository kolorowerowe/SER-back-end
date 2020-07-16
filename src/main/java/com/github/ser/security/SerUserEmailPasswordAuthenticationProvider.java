package com.github.ser.security;

import com.github.ser.exception.auth.InactiveUserException;
import com.github.ser.exception.auth.PasswordMissingException;
import com.github.ser.exception.auth.UsernameMissingException;
import com.github.ser.model.database.User;
import com.github.ser.service.UserService;
import com.github.ser.util.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

@Component
@Log4j2
public class SerUserEmailPasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public SerUserEmailPasswordAuthenticationProvider(UserService userService, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        log.info("User: " + auth.getPrincipal() + " attempting login");

        if (auth.getCredentials() == null) {
            log.debug("User: " + auth.getPrincipal() + " password is null");
            throw new PasswordMissingException("Password is null");
        }

        if (auth.getPrincipal() == null) {
            log.debug("User: " + auth.getPrincipal() + " username is null");
            throw new UsernameMissingException("Username is null");
        }
        String email = auth.getPrincipal().toString();
        String password = auth.getCredentials().toString();

        if (email.isEmpty()) {
            log.debug("User: " + auth.getPrincipal() + " email is empty");
            throw new UsernameMissingException("Username is empty");
        }
        if (password.isEmpty()) {
            log.debug("User: " + auth.getPrincipal() + " password is empty");
            throw new PasswordMissingException("Password is empty");
        }

        User user = userService.getUserByEmail(email);

        if (user == null){
            log.debug("User: " + auth.getPrincipal() + " does not exist");
            throw new BadCredentialsException("Credentials are invalid");
        }

        if (!user.getIsEnabled()) {
            log.debug("User: " + auth.getPrincipal() + " is disabled");
            throw new InactiveUserException("This user account is disabled");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {

            log.info("User: " + auth.getPrincipal() + " logged in successfully");
            user.setLastSeen(LocalDateTime.now());

            return new UsernamePasswordAuthenticationToken(user.getEmail(), null, Collections.singletonList(user.getRole()));
        } else {
            log.info("User: " + auth.getPrincipal() + " invalid password");
            throw new BadCredentialsException("Credentials are invalid");
        }

    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}

