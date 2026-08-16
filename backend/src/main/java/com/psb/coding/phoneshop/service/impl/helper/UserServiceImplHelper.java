package com.psb.coding.phoneshop.service.impl.helper;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.psb.coding.phoneshop.entity.Role;

public class UserServiceImplHelper {

	public static Set<SimpleGrantedAuthority> getAuthority(Set<Role> roles) {
		Set<SimpleGrantedAuthority> roleAuthz = roles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole())).collect(Collectors.toSet());
		Set<SimpleGrantedAuthority> authz = roles.stream().flatMap(permissions -> toGetPermission(permissions))
				.collect(Collectors.toSet());
		authz.addAll(roleAuthz);
		return authz;
	}

	private static Stream<SimpleGrantedAuthority> toGetPermission(Role role) {
		return role.getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()));
	}
}
