package com.psb.coding.phoneshop.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends ApiException {
	
	public ResourceNotFoundException(String resourceName, Long id) {
		super(HttpStatus.NOT_FOUND, "%s with id = %d not found".formatted(resourceName,id));
	}
}
