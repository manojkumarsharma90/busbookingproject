package com.busbooking.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${jwt.expiration}")
	private long jwtExpiration;

	public String generateToken(String userName, List<String> roles) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", roles);
		return createToken(claims, userName);
	}

	private String createToken(Map<String, Object> map, String userName) {
		return Jwts.builder()
				.claims(map)
				.subject(userName)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(getSignKey())
				.compact();
	}

	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(
				java.util.Base64.getEncoder().encodeToString(SECRET.getBytes()));
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	@SuppressWarnings("unchecked")
	public List<String> getRoles(String token) {
		return extractClaim(token, claims -> {
			Object rolesClaim = claims.get("roles");
			if (rolesClaim instanceof List<?>) {
				return ((List<?>) rolesClaim).stream()
						.map(Object::toString).toList();
			}
			return List.of();
		});
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
