//package com.intellect.financeTracker.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.Authentication;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//public class JwtProvider {
//
//    private static final SecretKey key =
//            Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
//
//    // Generate JWT token
//    public static String generateToken(Authentication auth){
//
//        return Jwts.builder()
//                .setIssuer("Raushan")
//                .setSubject(auth.getName()) // username
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
//                .signWith(key)
//                .compact();
//    }
//
//    // Extract username
//    public static String getUsernameFromJwtToken(String token){
//
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }
//
//    // Validate token
//    public static boolean validToken(String token){
//
//        Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token);
//
//        return true;
//    }
//}

package com.intellect.financeTracker.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

    private static final SecretKey key =
            Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth){

        return Jwts.builder()
                .setSubject(auth.getName())
                .setIssuer("restaurant-finance-tracker")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    public static String getUsername(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean validateToken(String token){

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return true;
    }
}