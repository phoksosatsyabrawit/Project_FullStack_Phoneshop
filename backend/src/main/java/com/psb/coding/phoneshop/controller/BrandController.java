package com.psb.coding.phoneshop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psb.coding.phoneshop.dto.BrandDTO;
import com.psb.coding.phoneshop.dto.ModelDTO;
import com.psb.coding.phoneshop.dto.PageDTO;
import com.psb.coding.phoneshop.entity.Brand;
import com.psb.coding.phoneshop.entity.Model;
import com.psb.coding.phoneshop.mapper.BrandMapper;
import com.psb.coding.phoneshop.mapper.ModelMapper;
import com.psb.coding.phoneshop.service.BrandService;
import com.psb.coding.phoneshop.service.ModelService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/brands")
public class BrandController {
	
	private final BrandService brandService;
	private final ModelService modelService;
	private final ModelMapper modelMapper;
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> createBrand(@RequestBody BrandDTO brandDTO){
		log.info("Create brand");
		Brand brand = BrandMapper.INSTANCE.toBrand(brandDTO);
		Brand brands = brandService.create(brand);
		return ResponseEntity.ok(brands);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getSingleBrand(@PathVariable Long id){
		Brand brand = brandService.getById(id);
		return ResponseEntity.ok(brand);
	}
	
	//@Operation(summary = "Get Brands")
	@PreAuthorize("hasAnyRole('ADMIN', 'FINANCE', 'SALE')")
	@GetMapping
	public ResponseEntity<?> getBrands(@RequestParam Map<String, String> params){
		Page<Brand> page = brandService.getBrands(params);
		PageDTO pageDTO = new PageDTO(page);
		//log.info("Current auth in controller: {}", SecurityContextHolder.getContext().getAuthentication());
		return ResponseEntity.ok(pageDTO);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{id}")
	public ResponseEntity<?> updateBrand(@RequestBody BrandDTO brandDTO){
		Brand brand = BrandMapper.INSTANCE.toBrand(brandDTO);
		Brand update = brandService.update(brand);
		return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(update));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteBrand(@PathVariable Long id){
		Brand brand = brandService.delete(id);
		return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(brand));
	}
	
	@GetMapping("{id}/models")
	public ResponseEntity<?> getModelsByBrand(@PathVariable Long id){
		List<Model> findByBrands = modelService.getByBrand(id);
		List<ModelDTO> lists = findByBrands.stream()
		.map(modelMapper::toModelDTO)
		.toList();
		return ResponseEntity.ok(lists);
	}
	
	/*@GetMapping
	public ResponseEntity<?> getBrands(@RequestParam Map<String, String> params){ // dynamic query
		List<Brand> brands = brandService.getBrands(params);
		return ResponseEntity.ok(brands);
	}*/
	
	/*@GetMapping
	public ResponseEntity<?> getBrands(@RequestParam String name){
		List<Brand> listBrands = brandService.getBrands(name);
		return ResponseEntity.ok(listBrands);
	}*/
}
