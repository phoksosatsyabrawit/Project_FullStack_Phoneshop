package com.psb.coding.phoneshop.dto;


import java.util.List;

import lombok.Data;

@Data
public class UserV1DTO {
	private String username;
	private String email;
	private String password;
	private List<String> roles;
	private List<String> permissions;
}
