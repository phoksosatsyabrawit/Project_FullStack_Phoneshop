package com.psb.coding.phoneshop.service;

import com.psb.coding.phoneshop.dto.LoginRequestDto;
import com.psb.coding.phoneshop.dto.LoginResultDTO;

public interface AuthService {
	//String authenticateUser(LoginRequestDto loginDto);
	LoginResultDTO login(LoginRequestDto loginDto);
}
