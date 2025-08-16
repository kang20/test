package com.task.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtHandler {
	private final String issuer;
	private final long ttlSeconds;
	private final String secretConfig;
	private SecretKey key;

	public JwtHandler(
		@Value("${jwt.issuer:sionic-ai-demo}") String issuer,
		@Value("${jwt.ttl-seconds:3600}") long ttlSeconds,
		@Value("${JWT_SECRET:#{null}}") String envSecret,
		@Value("${jwt.secret:#{null}}") String ymlSecret
	) {
		this.issuer = issuer;
		this.ttlSeconds = ttlSeconds;
		this.secretConfig = (envSecret != null) ? envSecret : ymlSecret;
	}

	@PostConstruct
	void init() {
		String s = (secretConfig != null)
			? secretConfig
			: "base64:yNq1qz3i0cQ0w4xQn1y1bqkq0rj2H9Cw0xS3eS9x4yU="; // 데모 기본값
		byte[] keyBytes = s.startsWith("base64:")
			? Decoders.BASE64.decode(s.substring(7))
			: s.getBytes(StandardCharsets.UTF_8);
		if (keyBytes.length < 32) throw new IllegalArgumentException("JWT secret must be >= 32 bytes");
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String issue(Long id, String email, String name, String role) {
		Instant now = Instant.now();
		return Jwts.builder()
			.setIssuer(issuer)
			.setSubject(String.valueOf(id))
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.plusSeconds(ttlSeconds)))
			.addClaims(Map.of("email", email, "name", name, "role", role))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Jws<Claims> parse(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
	}

	public long ttlSeconds(){ return ttlSeconds; }
}
