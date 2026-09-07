package com.psb.coding.phoneshop.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psb.coding.phoneshop.dto.LoginRequestDto;
import com.psb.coding.phoneshop.dto.LoginResultDTO;
import com.psb.coding.phoneshop.entity.RefreshToken;
import com.psb.coding.phoneshop.entity.User;
import com.psb.coding.phoneshop.service.AuthService;
import com.psb.coding.phoneshop.service.RefreshTokenService;
import com.psb.coding.phoneshop.service.impl.helper.JwtHelper;
import com.psb.coding.phoneshop.service.impl.helper.RefreshTokenHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	private final UserDetailsService userDetailsService;
	private final RefreshTokenHelper refreshTokenHelper;
	private final JwtHelper jwtHelper;
	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginRequestDto loginDto, HttpServletResponse res){
		LoginResultDTO loginResult = authService.login(loginDto);
		ResponseCookie accessToken = ResponseCookie
				.from("access_token", loginResult.accessToken())
				.httpOnly(true)
				.secure(false) // true if Https
				.path("/")
				.sameSite("Strict")
				.maxAge(Duration.ofMinutes(5))
				.build();

		ResponseCookie refreshToken = ResponseCookie
				.from("refresh_token", loginResult.refreshToken())
				.httpOnly(true)
				.secure(false)
				.path("/")
				.sameSite("Lax")
				.maxAge(Duration.ofDays(7))
				.build();
		
		res.addHeader(HttpHeaders.SET_COOKIE, accessToken.toString());
		res.addHeader(HttpHeaders.SET_COOKIE, refreshToken.toString());
		return ResponseEntity.ok().body("Login success.");
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(HttpServletRequest req, HttpServletResponse res){
		String refreshToken = refreshTokenHelper.getRefreshToken(req);
		if(refreshToken == null) {
			return ResponseEntity.status(401).build();
		}
		RefreshToken token = refreshTokenService.validate(refreshToken);
		User user = token.getUser();
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		String newAccessToken = jwtHelper.generateAccessToken(authentication);
		ResponseCookie cookie = ResponseCookie
					.from("access_token", newAccessToken)
					.httpOnly(true)
					.secure(false)
					.path("/")
					.sameSite("Strict")
					.maxAge(Duration.ofMinutes(5))
					.build();
			res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		return ResponseEntity.ok().body("Get new access token.");
	}
}
