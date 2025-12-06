package com.example.To_Do.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.To_Do.Entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final SecretKey key;
    private final long expirationTime;

    public JwtUtil(
            @Value("${jwt.secret_key}") String secretKey,
            @Value("${jwt.expiration_time}") long expirationTime
    ) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expirationTime = expirationTime;
    }
	
	
    
	public String generateToken(User user) {
		return Jwts.builder()
				.subject(user.getEmail())
				.claim("firstName", user.getFirstName())
				.claim("lastName", user.getLastName())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+expirationTime))
				.signWith(key)
				.compact();
	
		
	}
	
	public <O> O extractUsername(String token, Function<Claims,O> Resolver) {
		Claims claims = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		
		return Resolver.apply(claims);
				
			
				
	}
	
	public boolean isExpired(String token) {
		return extractUsername(token, claims->claims.getExpiration()).before(new Date());
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		String email = userDetails.getUsername();
		return !isExpired(token) && extractUsername(token, claims->claims.getSubject()).equals(email);
		
		
	}
			
	

}
