package com.psb.coding.phoneshop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psb.coding.phoneshop.dto.SetPriceDTO;
import com.psb.coding.phoneshop.dto.ProductDTO;
import com.psb.coding.phoneshop.dto.ProductImportHistoryDTO;
import com.psb.coding.phoneshop.entity.Product;
import com.psb.coding.phoneshop.mapper.ProductMapper;
import com.psb.coding.phoneshop.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;
	private final ProductMapper productMapper;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody ProductDTO productDTO){
		Product product = productMapper.toProduct(productDTO);
		product = productService.creat(product);
		return ResponseEntity.ok(productMapper.toProductDto(product));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		List<ProductDTO> products = productService.getAll();
		return ResponseEntity.ok(products);
	}
	
	@PostMapping("/import")
	public ResponseEntity<?> imports(@Valid @RequestBody ProductImportHistoryDTO productHistoryDto){
		productService.imports(productHistoryDto);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("{productId}/setSalePrice")
	public ResponseEntity<?> setSalePrice(@PathVariable Long productId,@Valid @RequestBody SetPriceDTO priceDto){
		Product setSalePrice = productService.setSalePrice(productId, priceDto);
		return ResponseEntity.ok(productMapper.toProductDto(setSalePrice));
	}
	
	@PostMapping("/uploads")
	public ResponseEntity<?> upload(@RequestParam MultipartFile file){
		Map<Integer, String> upload = productService.upload(file);
		return ResponseEntity.ok(upload);
	}
}
