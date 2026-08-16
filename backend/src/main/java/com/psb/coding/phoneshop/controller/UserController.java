package com.psb.coding.phoneshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psb.coding.phoneshop.dto.UserV1DTO;
import com.psb.coding.phoneshop.entity.User;
import com.psb.coding.phoneshop.mapper.UserMapper;
import com.psb.coding.phoneshop.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final UserMapper userMapper;

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserV1DTO dto){
		User user = userService.createUser(dto);
		return ResponseEntity.ok(userMapper.toUserV1DTO(user));
	}
}
