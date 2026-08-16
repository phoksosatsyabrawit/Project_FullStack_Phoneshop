package com.psb.coding.phoneshop.dto.report;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ExpenseReportDTO {
	private Long productId;
	private String productName;
	private Integer unit;
	private BigDecimal total;
}
