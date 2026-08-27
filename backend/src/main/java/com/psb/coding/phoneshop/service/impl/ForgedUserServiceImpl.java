package com.psb.coding.phoneshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.psb.coding.phoneshop.configuration.security.RoleEnum;
import com.psb.coding.phoneshop.dto.UserCreateDTO;
import com.psb.coding.phoneshop.entity.User;
import com.psb.coding.phoneshop.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ForgedUserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;

	@Override
	public Optional<UserAuthServiceImpl> findUserByUsername(String username) {
		List<UserAuthServiceImpl> userAuthServices = List.of(
				new UserAuthServiceImpl("steve", passwordEncoder.encode("steve123"), RoleEnum.FINANCE.getAuthorities(),
						true, true, true, true),
				new UserAuthServiceImpl("votey", passwordEncoder.encode("votey123"), RoleEnum.SALE.getAuthorities(), 
						true, true, true, true));
		return userAuthServices.stream().filter(u -> u.getUsername().equals(username)).findFirst();
	}

	@Override
	public User createUser(UserCreateDTO dto) {
		return null;
	}
}
