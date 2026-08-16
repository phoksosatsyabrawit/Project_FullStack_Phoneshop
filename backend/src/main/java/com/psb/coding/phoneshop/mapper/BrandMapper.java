package com.psb.coding.phoneshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.psb.coding.phoneshop.dto.BrandDTO;
import com.psb.coding.phoneshop.entity.Brand;

@Mapper
public interface BrandMapper {

	BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);
	Brand toBrand(BrandDTO dto);
	BrandDTO toBrandDTO(Brand entity);
}
