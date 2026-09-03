package com.psb.coding.phoneshop.enumeration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PermissionEnum {

	BRAND_READ("brand:read"), 
	BRAND_WRITE("brand:write");
	
	private String description;
}
