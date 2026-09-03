package com.psb.coding.phoneshop.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

	public AuditorAware<String> getAuditorAware(){
		return new AuditorAwareConfig();
	}
}
