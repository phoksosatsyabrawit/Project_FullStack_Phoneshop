package com.psb.coding.phoneshop.configuration.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.psb.coding.phoneshop.configuration.security.jwt.TokenVerifyFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final PasswordConfig passwordConfig;
	private final UserDetailsService userDetailsService;
	private final TokenVerifyFilter tokenVerifyFilter;

	@Bean
	public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {
		http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfig()))
				.authorizeHttpRequests(authz -> authz.requestMatchers("/auth/signin/**","/welcome.html", "/css/**", "/js/**",
						"/swagger-ui/**", "/v3/api-docs*/**").permitAll().anyRequest().authenticated())
				.addFilterBefore(tokenVerifyFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
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
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
	}

	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordConfig.passwordEncoder());
		return provider;
	}
	
	/*@Bean
	public InMemoryUserDetailsManager userDetailService() {
		UserDetails user1 = User.builder().username("sam").password(passwordEncoder.encode("sam123"))
				.authorities(RoleConfig.ADMIN.getAuthorities()).build();
		UserDetails user2 = User.builder().username("tey").password(passwordEncoder.encode("tey123"))
				.authorities(RoleConfig.SALE.getAuthorities()).build();
		return new InMemoryUserDetailsManager(user1, user2);
	}*/
}
