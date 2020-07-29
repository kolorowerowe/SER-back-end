package com.github.ser.util;

import com.github.ser.config.JwtTokenConfig;
import com.github.ser.enums.Role;
import com.github.ser.model.database.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Log4j2
public class JwtTokenUtil implements Serializable {

    private final JwtTokenConfig jwtTokenConfig;

    public JwtTokenUtil(JwtTokenConfig jwtTokenConfig) {
        this.jwtTokenConfig = jwtTokenConfig;
    }


    public String generateActivationTokenForUser(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("rol", Role.ACTIVATE_ACCOUNT.getAuthority())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenConfig.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, jwtTokenConfig.getSecret()).compact();
    }

    public String generateTokenForUser(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("rol", user.getRole().getAuthority())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenConfig.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, jwtTokenConfig.getSecret()).compact();
    }

    public String getEmailFromToken(String token) throws ExpiredJwtException {

        Claims claims = Jwts.parser()
                .setSigningKey(jwtTokenConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();


    }
}