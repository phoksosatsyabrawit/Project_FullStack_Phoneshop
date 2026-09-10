package com.psb.coding.phoneshop.service;

import com.psb.coding.phoneshop.dto.LoginRequestDto;
import com.psb.coding.phoneshop.entity.LoginResult;

public interface AuthService {
	//String authenticateUser(LoginRequestDto loginDto);
	LoginResult login(LoginRequestDto loginDto);
}
