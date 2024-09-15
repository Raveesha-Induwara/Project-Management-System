package com.raveesha.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    
//    static SecretKey key = Keys.hmacShaKeyFor(JWTConstant.SECRETE_KEY.getBytes());
    private final String secretKey;
    
    public JwtProvider() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    private  SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication auth) {
        return Jwts.builder()
                             .claim("email", auth.getName())
                             .issuedAt(new Date())
                             .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
                             .signWith(getKey())
                             .compact();
    }
    
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                            .verifyWith(getKey())
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();

        return String.valueOf(claims.get("email"));
    }
}
