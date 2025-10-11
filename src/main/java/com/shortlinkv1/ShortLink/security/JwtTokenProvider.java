package com.shortlinkv1.ShortLink.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration = 86400;


    public String generateToken(String email) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(email) // What is the token linked to
                .setIssuedAt(Date.from(now)) // When issued
                .setExpiration(Date.from(now.plus(expiration, ChronoUnit.SECONDS))) // When will expire
                .signWith(SignatureAlgorithm.HS512, secret) // Signature
                .compact(); // The end, forms the unique JWT token

    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Boolean validateToken(String token) {

        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

}
