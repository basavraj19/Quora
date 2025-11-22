package com.example.Quora.Utils;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {

	@Value("${secertKey}")
	private String secretKey;

	public Key getKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public String generateToken(final String userName) {
		return Jwts.builder().setSubject(userName).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 1000 * 60 * 5)).signWith(getKey()).compact();
	}

	public Claims extractAllClaims(final String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}

	public <T> T extarctClaims(final String token, Function<Claims, T> resolver) {
		return resolver.apply(extractAllClaims(token));
	}

	public String extractUserName(final String token) {
		return extarctClaims(token, Claims::getSubject);
	}

	public Date extractExpiration(final String token) {
		return extarctClaims(token, Claims::getExpiration);
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}
