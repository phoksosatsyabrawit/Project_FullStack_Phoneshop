package com.psb.coding.phoneshop.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.psb.coding.phoneshop.entity.RefreshToken;
import com.psb.coding.phoneshop.entity.User;
import com.psb.coding.phoneshop.repository.RefreshTokenRepository;
import com.psb.coding.phoneshop.service.RefreshTokenService;
import com.psb.coding.phoneshop.service.impl.helper.RefreshTokenHelper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	private final RefreshTokenHelper refreshTokenHelper;
	private static final long REFRESH_TOKEN_DAYS = 7;
	
	@Override
	public String create(User user) {
		String rawToken = refreshTokenHelper.generateRandomToken();
		String tokenHash = refreshTokenHelper.hash(rawToken);
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUser(user);
		refreshToken.setTokenHash(tokenHash);
		refreshToken.setCreatedAt(Instant.now());
		refreshToken.setExpiresAt(Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS));
		refreshToken.setRevoked(false);
		refreshTokenRepository.save(refreshToken);
		return rawToken;
	}

	@Override
	public RefreshToken validate(String rawToken) {
		if(rawToken == null || rawToken.isBlank()) {
			throw new RuntimeException("Invalid refresh token.");
		}
		Optional<RefreshToken> refreshToken = refreshTokenRepository.findByTokenHash(refreshTokenHelper.hash(rawToken));
		if(refreshToken.isEmpty()) {
			throw new RuntimeException("Invalid refresh token.");
		}
		RefreshToken token = refreshToken.get();
		if(token.isRevoked()) {
			throw new RuntimeException("Refresh token has been revoked.");
		}
		if(token.getExpiresAt().isBefore(Instant.now())) {
			throw new RuntimeException("Refresh token has expired.");
		}
		return token;
	}

	@Override
	public void revoke(RefreshToken token) {
		token.setRevoked(true);
		refreshTokenRepository.save(token);
	}

	@Transactional
	@Override
	public String rotate(RefreshToken oldToken) {
		revoke(oldToken);
		return create(oldToken.getUser());
	}
}
