package com.jai.MiniBlog.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
   @Value("${jwt.secret}")
   private String secret;
 
   public String generateToken(int userId) {
	   SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
	   
	   return Jwts.builder()
			   .subject(String.valueOf(userId))
			   .issuedAt(new Date())
			   .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
			   .signWith(key)
			   .compact();
   }
   
   public int extractUserId(String token) {
	   SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
	   
	   String userIdStr = Jwts.parser()
			   .verifyWith(key)
			   .build()
			   .parseSignedClaims(token)
			   .getPayload()
			   .getSubject();
	   
	   return Integer.parseInt(userIdStr);
   }
}
