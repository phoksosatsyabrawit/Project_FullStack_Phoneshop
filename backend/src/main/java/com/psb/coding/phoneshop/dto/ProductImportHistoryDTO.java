package com.psb.coding.phoneshop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductImportHistoryDTO {
	
	@NotNull(message = "Product id should not be null.")
	private Long productId;
	
	@Min(value = 1, message = "Import Unit must be greater than zero.")
	private Integer importUnit;
	
	@DecimalMin(value = "0.001", message = "Costing must not be zero.")
	private BigDecimal pricePerUnit;
	
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm[:ss]")
	@NotNull(message = "Date Import Required")
	private LocalDateTime importDate;
}
