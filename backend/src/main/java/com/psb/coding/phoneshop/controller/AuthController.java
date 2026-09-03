package com.psb.coding.phoneshop.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psb.coding.phoneshop.dto.LoginRequestDto;
import com.psb.coding.phoneshop.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginRequestDto loginDto){
		String jwt = authService.authenticateUser(loginDto);
		ResponseCookie cookie = ResponseCookie.
				from("access_token", jwt)
				.httpOnly(true)
				.secure(false) // true if Https
				.path("/")
				.sameSite("Strict")
				.maxAge(Duration.ofMinutes(15))
				.build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Login Success.");
	}
}
