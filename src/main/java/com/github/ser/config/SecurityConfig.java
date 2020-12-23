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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                .cors()
                .configurationSource(corsConfiguration())
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/check-token").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/verify/**").permitAll()
                .antMatchers("/user/password/set").hasRole("ACTIVATE_ACCOUNT")
                .antMatchers("/user/**").hasAnyRole("SYSTEM_ADMIN", "ORGANIZER_EDITOR", "ORGANIZER_VIEWER", "COMPANY_EDITOR", "COMPANY_VIEWER")
                .antMatchers("/company/**").hasAnyRole("SYSTEM_ADMIN", "ORGANIZER_EDITOR", "ORGANIZER_VIEWER", "COMPANY_EDITOR", "COMPANY_VIEWER")
                .antMatchers("/sponsorship-package/**").hasAnyRole("SYSTEM_ADMIN", "ORGANIZER_EDITOR", "ORGANIZER_VIEWER", "COMPANY_EDITOR", "COMPANY_VIEWER")
                .antMatchers("/deadline/**").hasAnyRole("SYSTEM_ADMIN", "ORGANIZER_EDITOR", "ORGANIZER_VIEWER")
                .antMatchers("/equipment/**").hasAnyRole("SYSTEM_ADMIN", "ORGANIZER_EDITOR", "ORGANIZER_VIEWER")
                .antMatchers("/statistics/**").hasAnyRole("SYSTEM_ADMIN", "ORGANIZER_EDITOR", "ORGANIZER_VIEWER")
                .antMatchers("/event-config/**").hasAnyRole("SYSTEM_ADMIN", "ORGANIZER_EDITOR", "ORGANIZER_VIEWER", "COMPANY_EDITOR", "COMPANY_VIEWER")
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

    private UrlBasedCorsConfigurationSource corsConfiguration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addExposedHeader("x-suggested-filename");
        source.registerCorsConfiguration("/**", config);
        return source;
    }




}
