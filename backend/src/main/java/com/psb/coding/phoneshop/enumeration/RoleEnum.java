package com.psb.coding.phoneshop.enumeration;

import static com.psb.coding.phoneshop.enumeration.PermissionEnum.BRAND_READ;
import static com.psb.coding.phoneshop.enumeration.PermissionEnum.BRAND_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleEnum {

	FINANCE(Set.of(BRAND_READ, BRAND_WRITE)), 
	SALE(Set.of(BRAND_READ)),
	HR(Set.of(BRAND_READ, BRAND_WRITE));
	
	
	private Set<PermissionEnum> permission;
	
	public Set<SimpleGrantedAuthority> getAuthorities(){
		Set<SimpleGrantedAuthority> authorities = this.permission.stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getDescription())).collect(Collectors.toSet());
		SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + this.name());
		authorities.add(role);
		return authorities;
	}
}
