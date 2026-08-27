package com.psb.coding.phoneshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class openApiConfig {
	
	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Enterprise PhoneShop Management API")
                        .version("1.0.0")
                        .description("Production-grade API documentation for handling global phoneshop management.")
                        .termsOfService("https://psb.com")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
	}
}
