package com.psb.coding.phoneshop.specification;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductImportHistoryFilter {
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
