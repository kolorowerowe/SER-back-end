package com.github.ser.config;

import com.github.ser.security.JwtAuthorizationFilter;
import com.github.ser.security.JwtLoginFilter;
import com.github.ser.security.SerUserEmailPasswordAuthenticationProvider;
import com.github.ser.service.UserService;
import com.github.ser.util.JwtTokenUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider passwordAuthenticationProvider;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;


    public SecurityConfig(SerUserEmailPasswordAuthenticationProvider passwordAuthenticationProvider,
                          JwtTokenUtil jwtTokenUtil,
                          UserService userService) {

        this.passwordAuthenticationProvider = passwordAuthenticationProvider;
        this.authenticationManager = getAuthenticationManager();
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/**").hasRole("SYSTEM_ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter( new JwtLoginFilter(authenticationManager, jwtTokenUtil, userService))
                .addFilter( new JwtAuthorizationFilter(authenticationManager, jwtTokenUtil, userService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(passwordAuthenticationProvider);
    }

    private AuthenticationManager getAuthenticationManager() {
        List<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(passwordAuthenticationProvider);
        return new ProviderManager(authenticationProviderList);
    }


}