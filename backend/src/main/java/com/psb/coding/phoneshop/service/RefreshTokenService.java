package com.psb.coding.phoneshop.service;

import com.psb.coding.phoneshop.entity.RefreshToken;
import com.psb.coding.phoneshop.entity.User;

public interface RefreshTokenService {
	String create(User user);
	RefreshToken validate(String rawToken);
	void revoke(RefreshToken token);
	String rotate(RefreshToken oldToken);
}
