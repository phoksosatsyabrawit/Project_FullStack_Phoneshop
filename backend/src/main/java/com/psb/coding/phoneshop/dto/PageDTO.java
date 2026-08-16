package com.psb.coding.phoneshop.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PageDTO {

	private List<?> page;
	private PaginationDTO pagination;
	
	public PageDTO(Page<?> page) {
		this.page = page.getContent();
		this.pagination = PaginationDTO.builder()
				.pageNumber(page.getNumber() + 1)
				.pageSize(page.getSize())
				.totalElements(page.getTotalElements())
				.totalPages(page.getTotalPages())
				.first(page.isFirst())
				.last(page.isLast())
				.empty(page.isEmpty())
				.build();
	}
}
