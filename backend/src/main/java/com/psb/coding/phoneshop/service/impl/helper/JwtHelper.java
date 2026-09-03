package com.psb.coding.phoneshop.service.impl.helper;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtHelper {

	@Value("${jwt.secret}")
	private String secretKeyBase64;

	@Value("${jwt.expiration-ms}")
	private Long expiration;
	
	private Date now = new Date();

	public SecretKey getSignInKey() {
		byte[] keyBtye = Base64.getDecoder().decode(secretKeyBase64);
		return Keys.hmacShaKeyFor(keyBtye);
	}

	public String generateToken(Authentication authentication) {
		List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		return Jwts.builder()
				.subject(authentication.getName())
				.issuedAt(now)
				.claim("Authorities", authorities)
				.issuer("psbcode.com")
				.expiration(new Date(now.getTime() + expiration))
				.signWith(getSignInKey())
				.compact();
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractAllClaims(token)
				.getExpiration()
				.before(new Date());
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
