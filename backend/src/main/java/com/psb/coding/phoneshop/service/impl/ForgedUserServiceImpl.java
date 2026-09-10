package com.psb.coding.phoneshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.psb.coding.phoneshop.dto.UserCreateDTO;
import com.psb.coding.phoneshop.entity.User;
import com.psb.coding.phoneshop.entity.UserAuth;
import com.psb.coding.phoneshop.enumeration.RoleEnum;
import com.psb.coding.phoneshop.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ForgedUserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;

	@Override
	public Optional<UserAuth> findUserByUsername(String username) {
		List<UserAuth> userAuthServices = List.of(
				new UserAuth("steve", passwordEncoder.encode("steve123"), RoleEnum.FINANCE.getAuthorities(),
						true, true, true, true),
				new UserAuth("votey", passwordEncoder.encode("votey123"), RoleEnum.SALE.getAuthorities(), 
						true, true, true, true));
		return userAuthServices.stream().filter(u -> u.getUsername().equals(username)).findFirst();
	}

	@Override
	public User createUser(UserCreateDTO dto) {
		return null;
	}
}
