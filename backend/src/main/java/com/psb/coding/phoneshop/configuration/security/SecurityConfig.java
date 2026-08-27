package com.psb.coding.phoneshop.configuration.security;

import java.util.List;

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
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	//private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;

	@Bean
	public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {
		http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfig()))
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authz -> authz.requestMatchers("/auth/signin/**","/welcome.html", "/css/**", "/js/**",
						"/swagger-ui/**", "/v3/api-docs*/**").permitAll().anyRequest().authenticated());
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public CorsConfigurationSource corsConfig() {
		CorsConfiguration cors = new CorsConfiguration();
		cors.setAllowedOrigins(List.of("http://localhost:4200"));
		cors.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTION"));
		cors.setAllowedHeaders(List.of("*"));
		cors.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", cors);
		return source;
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
		//provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
}
