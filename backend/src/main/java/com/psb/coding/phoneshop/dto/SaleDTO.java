package com.psb.coding.phoneshop.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psb.coding.phoneshop.dto.report.ProductSaleDTO;

import lombok.Data;

@Data
public class SaleDTO {
	
	private List<ProductSaleDTO> products;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm[:ss]")
	private LocalDateTime saleDate;
}
