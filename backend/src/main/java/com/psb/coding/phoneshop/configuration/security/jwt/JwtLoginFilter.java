package com.psb.coding.phoneshop.configuration.security.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psb.coding.phoneshop.dto.LoginRequestDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// Deserialize json to java object
		ObjectMapper map = new ObjectMapper();
		try {
			LoginRequestDto login = map.readValue(request.getInputStream(), LoginRequestDto.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
			Authentication authenticate = authenticationManager.authenticate(authentication);
			return authenticate;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String key = "asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;";
		List<@Nullable String> authzList = authResult.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		String token = Jwts.builder()
			//payload
			.subject(authResult.getName())
			.issuedAt(new Date())
			.claim("Authorities", authzList)
			//sign
			.signWith(Keys.hmacShaKeyFor(key.getBytes()))
			.issuer("psb.com")
			.expiration(java.sql.Date.valueOf(LocalDate.now()))
			.compact();
		response.setHeader("Authorization", "Bearer " + token);
	}
}
