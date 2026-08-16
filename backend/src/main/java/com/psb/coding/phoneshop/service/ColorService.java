package com.psb.coding.phoneshop.service;

import org.mapstruct.Named;

import com.psb.coding.phoneshop.entity.Color;

public interface ColorService {
	
	@Named("getColorById")
	Color getById(Long id);
}
