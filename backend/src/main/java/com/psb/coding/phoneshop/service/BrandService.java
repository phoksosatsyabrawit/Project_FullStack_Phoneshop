package com.psb.coding.phoneshop.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.psb.coding.phoneshop.entity.Brand;

public interface BrandService {
	Brand create(Brand brand);
	Brand getById(Long id);//return single brand
	Brand update(Long id, Brand brandUpdate);
	Brand delete(Long id, Brand brandDelete);
	//List<Brand> getBrands(String name);
	//List<Brand> getBrands(Map<String, String> params); //dynamic query
	Page<Brand> getBrands(Map<String, String> params);
}
