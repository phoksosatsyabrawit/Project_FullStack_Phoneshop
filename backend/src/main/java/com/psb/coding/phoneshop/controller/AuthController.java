package com.psb.coding.phoneshop.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psb.coding.phoneshop.dto.LoginRequestDto;
import com.psb.coding.phoneshop.service.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginRequestDto loginDto){
		String jwt = authService.authenticateUser(loginDto);
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", "Bearer" + jwt);
		return ResponseEntity.ok().headers(header).build();
	}
}
