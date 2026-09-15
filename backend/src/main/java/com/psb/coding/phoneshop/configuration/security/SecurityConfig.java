package com.psb.coding.phoneshop.configuration.security;

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

import com.psb.coding.phoneshop.configuration.security.jwt.CookieTokenFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final PasswordConfig passwordConfig;
	//private final BearerTokenFilter bearerTokenFilter;
	private final CookieTokenFilter cookieTokenFilter;
	private final CorsConfig corsConfig;
	private final UserDetailsService userDetailsService;

	@Bean
	public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {
		http.csrf(csrf -> csrf.disable()) 	//.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				.cors(cors -> cors.configurationSource(corsConfig.corsConfiguration()))
				.addFilterBefore(cookieTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authz -> authz
				.requestMatchers("/auth/signin", "/auth/refresh", "/welcome.html", "/css/**", "/js/**",
						"/swagger-ui/**", "/v3/api-docs*/**").permitAll()
				.requestMatchers("/brands/**").authenticated()
				.anyRequest().authenticated())
				.exceptionHandling(ex -> ex
				.authenticationEntryPoint((req, res, authException) -> {
					res.setContentType("application/json");
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					res.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\"}");})
				.accessDeniedHandler((req, res, accessDenied) -> {
					res.setContentType("application/json");
					res.setStatus(HttpServletResponse.SC_FORBIDDEN);
					res.getWriter().write("{\"error\": \"Forbidden\", \"message\": \"" + accessDenied.getMessage() + "\"}");}));
		return http.build();
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
