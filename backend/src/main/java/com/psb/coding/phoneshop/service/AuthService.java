package com.psb.coding.phoneshop.service;

import com.psb.coding.phoneshop.dto.LoginRequestDto;

public interface AuthService {
	String authenticateUser(LoginRequestDto loginDto);
}
