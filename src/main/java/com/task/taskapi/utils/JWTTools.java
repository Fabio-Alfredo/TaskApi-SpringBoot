package com.task.taskapi.utils;

import com.task.taskapi.domain.models.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.exptime}")
    private Integer exp;

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+exp) )
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Boolean verifyToken (String token){
        try{
            JwtParser parse = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build();
            parse.parse(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getUserByEmailFrom(String token){
        try{
            JwtParser parse = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build();

            return parse.parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }catch (Exception e){
            return null;
        }
    }

}
