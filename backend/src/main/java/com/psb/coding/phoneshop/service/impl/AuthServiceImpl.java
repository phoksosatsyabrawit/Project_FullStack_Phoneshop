package com.psb.coding.phoneshop.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.psb.coding.phoneshop.dto.LoginRequestDto;
import com.psb.coding.phoneshop.entity.LoginResult;
import com.psb.coding.phoneshop.entity.User;
import com.psb.coding.phoneshop.repository.UserRepository;
import com.psb.coding.phoneshop.service.AuthService;
import com.psb.coding.phoneshop.service.RefreshTokenService;
import com.psb.coding.phoneshop.service.impl.helper.JwtHelper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final JwtHelper jwtHelper;
	private final UserRepository userRepository;
	private final RefreshTokenService refreshTokenService;
	private final AuthenticationManager authenticationManager;
	
	@Override
	public LoginResult login(LoginRequestDto loginDto) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow();
		String accessToken = jwtHelper.generateAccessToken(user);
		String refreshToken = refreshTokenService.create(user);
		return new LoginResult(accessToken, refreshToken);
	}
}
