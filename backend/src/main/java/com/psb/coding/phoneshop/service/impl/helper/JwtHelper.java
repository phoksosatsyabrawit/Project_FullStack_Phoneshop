package com.psb.coding.phoneshop.service.impl.helper;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psb.coding.phoneshop.dto.LoginRequestDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtHelper {

	@Value("${jwt.secret}")
	private String secretKeyBase64;

	@Value("${jwt.expiration-ms}")
	private Long expiration;

	private final AuthenticationManager authenticationManager;
	private final HttpServletRequest req;

	public SecretKey getSignInKey() {
		byte[] keyBtye = Base64.getDecoder().decode(secretKeyBase64);
		return Keys.hmacShaKeyFor(keyBtye);
	}

	public String generateToken(LoginRequestDto loginDto) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expiration);
		ObjectMapper mapper = new ObjectMapper();
		try {
			LoginRequestDto loginMap = mapper.readValue(req.getInputStream(), loginDto.getClass());
			Authentication authenticateUser = new UsernamePasswordAuthenticationToken(loginMap.getUsername(),
					loginMap.getPassword());
			Authentication authentication = authenticationManager.authenticate(authenticateUser);
			List<String> authz = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
			return Jwts.builder().subject(authentication.getName()).issuedAt(now).claim("Authorities", authz)
					.issuer("psbcode.com").expiration(expiry).signWith(getSignInKey()).compact();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
