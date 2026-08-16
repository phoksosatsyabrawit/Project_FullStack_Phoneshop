package com.psb.coding.phoneshop.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

import com.psb.coding.phoneshop.configuration.security.jwt.JwtLoginFilter;
import com.psb.coding.phoneshop.configuration.security.jwt.TokenVerifyFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager auth)
			throws Exception {
		http.csrf(csrf -> csrf.disable()).addFilter(new JwtLoginFilter(auth))
				.addFilterAfter(new TokenVerifyFilter(), JwtLoginFilter.class)
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authz -> authz.requestMatchers("/login", "/welcome.html", "/css/**", "/js/**",
						"/swagger-ui/**", "/v3/api-docs*/**", "/users/**", "/brands/**").permitAll().anyRequest().authenticated());
		return http.build();
	}

	/*@Bean
	public InMemoryUserDetailsManager userDetailService() {
		UserDetails user1 = User.builder().username("sam").password(passwordEncoder.encode("sam123"))
				.authorities(RoleConfig.ADMIN.getAuthorities()).build();
		UserDetails user2 = User.builder().username("tey").password(passwordEncoder.encode("tey123"))
				.authorities(RoleConfig.SALE.getAuthorities()).build();
		return new InMemoryUserDetailsManager(user1, user2);
	}*/

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
	}

	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
}
