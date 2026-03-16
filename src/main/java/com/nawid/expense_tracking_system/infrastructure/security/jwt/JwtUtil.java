package com.nawid.expense_tracking_system.infrastructure.security.jwt;



import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

//    private final String SECRET = "werdftcvgbdftcvgftcvgDFCVyrtXCVTyvgBTcYVGBH";
//    private final long EXPIRATION = 86400000;

    @Value("${app.jwt-secret}")
    private String SECRET;
    @Value("${app.jwt-expiration-milliseconds}")
    private long EXPIRATION;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
