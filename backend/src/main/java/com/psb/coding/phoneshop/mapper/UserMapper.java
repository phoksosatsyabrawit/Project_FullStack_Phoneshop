package com.psb.coding.phoneshop.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.psb.coding.phoneshop.dto.UserV1DTO;
import com.psb.coding.phoneshop.entity.Permission;
import com.psb.coding.phoneshop.entity.Role;
import com.psb.coding.phoneshop.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	@Mapping(target = "roles", ignore = true) // roles resolved separately in the service, not from DTO
    User toUser(UserV1DTO dto);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleToName")
    @Mapping(target = "permissions", source = "roles", qualifiedByName = "roleToPermission")
    UserV1DTO toUserV1DTO(User entity);
    
    // Tell MapStruct how to convert a single Role -> String
    default String map(Role role) {
    	return role.getRole();
    }
    
    // Tell MapStruct how to convert Set<Role> -> List<String>
    @Named("roleToName")
    default List<String> mapRoles(Set<Role> roles){
    	return roles.stream()
    			.map(Role::getRole).collect(Collectors.toList());
    }
    
    @Named("roleToPermission")
    default List<String> mapPermissions(Set<Role> roles){
    	if(roles == null) return Collections.emptyList();
    	return roles.stream().flatMap(role -> role.getPermissions() == null ? Stream.empty() : role.getPermissions().stream())
    			.map(Permission::getPermission).distinct().collect(Collectors.toList());
    }
}