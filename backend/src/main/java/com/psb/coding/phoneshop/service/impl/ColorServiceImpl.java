package com.psb.coding.phoneshop.service.impl;


import org.springframework.stereotype.Service;

import com.psb.coding.phoneshop.entity.Color;
import com.psb.coding.phoneshop.exception.ResourceNotFoundException;
import com.psb.coding.phoneshop.repository.ColorRepository;
import com.psb.coding.phoneshop.service.ColorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ColorServiceImpl implements ColorService {
	
	private final ColorRepository colorRepository;

	@Override
	public Color getById(Long id) {
		return colorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Color", id));
	}
}
