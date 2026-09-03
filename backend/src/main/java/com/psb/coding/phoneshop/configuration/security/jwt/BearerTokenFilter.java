package com.psb.coding.phoneshop.configuration.security.jwt;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psb.coding.phoneshop.service.impl.helper.JwtHelper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BearerTokenFilter extends OncePerRequestFilter {
	
	
	private final JwtHelper jwtHelper;
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		if(Objects.isNull(header) || !header.contains("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = header.substring(7);
		
		try {
			Claims payload = Jwts.parser()
					.verifyWith(jwtHelper.getSignInKey()) // set the signed key
					.build() 										// build actual parser
					.parseSignedClaims(token)						// parse & verify
					.getPayload();									// get claims
				String username = payload.getSubject();
				List<String> authorities = (List<String>) payload.get("Authorities");
				List<SimpleGrantedAuthority> authzList = authorities.stream()
						.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
				Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authzList);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				filterChain.doFilter(request, response);
		}catch(ExpiredJwtException e) {
			log.info(e.getMessage());
			errorResponse(response, HttpStatus.UNAUTHORIZED, "Token Expired", e.getMessage());
		}catch(JwtException e) {
			log.info(e.getMessage());
			errorResponse(response, HttpStatus.UNAUTHORIZED, "Token Invalid", e.getMessage());
		}catch(AccessDeniedException e) {
			errorResponse(response, HttpStatus.UNAUTHORIZED, "Unauthorized", e.getMessage());
		}
	}
	
	private void errorResponse(HttpServletResponse res, HttpStatus status, String code, String message) throws IOException {
		res.setStatus(status.value());
		res.setContentType("application/json");
		
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("Code", code);
		response.put("Status", status.value());
		response.put("Message", message);
		
		res.getWriter().write(mapper.writeValueAsString(response));
		res.getWriter().flush();
	}
}
