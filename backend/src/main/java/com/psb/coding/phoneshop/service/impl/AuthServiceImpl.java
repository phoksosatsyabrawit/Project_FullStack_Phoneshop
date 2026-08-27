package com.psb.coding.phoneshop.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psb.coding.phoneshop.dto.LoginRequestDto;
import com.psb.coding.phoneshop.service.AuthService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final HttpServletRequest request;

	@Override
	public String authenticateUser(LoginRequestDto loginDto) {
		ObjectMapper map = new ObjectMapper();
		try {
			LoginRequestDto loginDtos = map.readValue(request.getInputStream(), LoginRequestDto.class);
			Authentication authenticateUser = new UsernamePasswordAuthenticationToken(loginDtos.getUsername(), loginDtos.getPassword());
			Authentication authentication = authenticationManager.authenticate(authenticateUser);
			String screteKey = "asdfjkl;!@#$%asdfjkl;!@#$%asdfjkl;!@#$%";
			List<String> authz = authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
			String jwt = Jwts.builder()
					.subject(authentication.getName())
					.issuedAt(new Date())
					.claim("Authorities", authz)
					.signWith(Keys.hmacShaKeyFor(screteKey.getBytes()))
					.issuer("psbcode.com")
					.expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
					.compact();
			return jwt;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
