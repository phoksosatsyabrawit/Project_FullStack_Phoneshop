package com.psb.coding.phoneshop.service.impl;

import com.psb.coding.phoneshop.service.impl.helper.JwtHelper;
import org.springframework.stereotype.Service;

import com.psb.coding.phoneshop.dto.LoginRequestDto;
import com.psb.coding.phoneshop.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final JwtHelper jwtHelper;

	@Override
	public String authenticateUser(LoginRequestDto loginDto) {
		return jwtHelper.generateToken(loginDto);
	}
}
