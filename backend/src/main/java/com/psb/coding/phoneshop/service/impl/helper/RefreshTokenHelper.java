package com.psb.coding.phoneshop.service.impl.helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenHelper {

	private final SecureRandom secureRandom;
	
	public String generateRandomToken() {
		byte[] bytes = new byte[64];
		secureRandom.nextBytes(bytes);
		
		return Base64.getUrlEncoder()
				.withoutPadding()
				.encodeToString(bytes);
	}
	
	public String hash(String token) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte [] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(hash);
		}catch(NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 algorithm not available.", e);
		}
	}

	public String getRefreshToken(HttpServletRequest req) {
		if(req.getCookies() == null) {
			return null;
		}
		for(Cookie cookie : req.getCookies()) {
			if("refresh_token".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
