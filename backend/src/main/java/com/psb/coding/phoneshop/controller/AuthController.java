package com.psb.coding.phoneshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psb.coding.phoneshop.dto.LoginRequestDto;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	//private final AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginRequestDto loginDto){
		return ResponseEntity.ok(loginDto);
	}
}
