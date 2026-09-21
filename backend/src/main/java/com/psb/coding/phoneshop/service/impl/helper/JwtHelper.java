package com.psb.coding.phoneshop.service.impl.helper;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.psb.coding.phoneshop.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtHelper {

	@Value("${jwt.secret}")
	private String secretKeyBase64;
	private static final long EXPIRY = 5 * 60 * 1000;
	
	public SecretKey getSignInKey() {
		byte[] keyBtye = Base64.getDecoder().decode(secretKeyBase64);
		return Keys.hmacShaKeyFor(keyBtye);
	}

	public String generateAccessToken(User user) {
		Date now = new Date();
		List<String> authorities = UserServiceImplHelper.getAuthority(user.getRoles()).stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		return Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(new Date())
				.claim("authorities", authorities)
				.issuer("psbcode.com")
				.expiration(new Date(now.getTime() + EXPIRY)) //5 minute
				.signWith(getSignInKey())
				.compact();
	}
	
	public String getAccessToken(HttpServletRequest req) {
		if(req.getCookies() == null) {
			return null;
		}
		for(Cookie cookie: req.getCookies()) {
			if("access_token".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
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
