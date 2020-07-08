package com.github.ser.util;

import com.github.ser.config.JwtTokenConfig;
import com.github.ser.exception.TokenExpiredException;
import com.github.ser.model.database.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {

    private final JwtTokenConfig jwtTokenConfig;

    public JwtTokenUtil(JwtTokenConfig jwtTokenConfig) {
        this.jwtTokenConfig = jwtTokenConfig;
    }


    public String generateTokenForUser(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("rol", user.getRole().getAuthority())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenConfig.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, jwtTokenConfig.getSecret()).compact();
    }

    public String getEmailFromToken(String token) {


        Claims claims = Jwts.parser()
                .setSigningKey(jwtTokenConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();

        Date expirationDate = claims.getExpiration();
        if (expirationDate.before(new Date())) {
            throw new TokenExpiredException("Request to parse expired token");
        }

        return claims.getSubject();
    }
}