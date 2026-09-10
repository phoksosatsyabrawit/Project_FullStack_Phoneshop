package com.psb.coding.phoneshop.service;

import java.util.Optional;

import com.psb.coding.phoneshop.dto.UserCreateDTO;
import com.psb.coding.phoneshop.entity.User;
import com.psb.coding.phoneshop.entity.UserAuth;

public interface UserService {
	Optional<UserAuth> findUserByUsername(String username);
	User createUser(UserCreateDTO dto);
}
