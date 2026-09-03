package com.psb.coding.phoneshop.configuration.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.psb.coding.phoneshop.service.impl.helper.JwtHelper;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieTokenFilter extends OncePerRequestFilter {
	
	private final JwtHelper jwtHelper;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getTokenFromCookie(request);
		try {
			if(token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				String username = jwtHelper.extractUsername(token);
				if(username != null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					if(jwtHelper.isTokenValid(token, userDetails)) {
						UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						authenticate.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authenticate);
					}
				}
			}
		}catch(JwtException | IllegalArgumentException e) {
			// Invalid or expired JWT.
			// Do not authenticate the request
			SecurityContextHolder.clearContext();
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getTokenFromCookie(HttpServletRequest req) {
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
}
