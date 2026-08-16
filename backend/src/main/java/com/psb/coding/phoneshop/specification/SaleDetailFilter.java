package com.psb.coding.phoneshop.specification;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SaleDetailFilter {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
