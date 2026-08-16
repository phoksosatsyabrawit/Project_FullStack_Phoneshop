package com.psb.coding.phoneshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psb.coding.phoneshop.dto.SaleDTO;
import com.psb.coding.phoneshop.service.SaleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/sales")
public class SaleController {

	private final SaleService saleService;

	@PostMapping
	public ResponseEntity<?> sale(@RequestBody SaleDTO saleDto) {
		saleService.sale(saleDto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("{saleId}/void")
	public ResponseEntity<?> voidSale(@PathVariable Long saleId) {
		saleService.voidSale(saleId);
		return ResponseEntity.ok().build();
	}
}
