package com.psb.coding.phoneshop.configuration.security;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecureRandomConfig {

	@Bean
	public SecureRandom secureRandom() {
		return new SecureRandom();
	}
}
