package com.psb.coding.phoneshop.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {
	private Long modelId;
	private Long colorId;
	private Long id;
	private String name;
	private BigDecimal salePrice;
}
