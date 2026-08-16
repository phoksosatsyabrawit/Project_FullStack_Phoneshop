package com.psb.coding.phoneshop.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class PriceDTO {
	
	@DecimalMin(value = "0.001", message = "Price must not be zero.")
	private BigDecimal salePrice;
}
