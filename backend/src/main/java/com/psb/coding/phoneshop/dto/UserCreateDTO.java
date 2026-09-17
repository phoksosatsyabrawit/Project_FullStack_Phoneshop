package com.psb.coding.phoneshop.dto;


import java.util.Set;

import lombok.Data;

@Data
public class UserCreateDTO {
	private String username;
	private String email;
	private String password;
	private Set<String> roles;
}
