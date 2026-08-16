package com.psb.coding.phoneshop.page.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PageUtil {

	Integer DEFAULT_PAGE_NUMBER = 1;
	Integer DEFAULT_PAGE_LIMIT = 2;
	String PAGE_LIMIT = "_limit";
	String PAGE_NUMBER = "_page";
	
	static Pageable pageRequest(Integer pageNumber, Integer pageSize) {
		if(pageNumber < DEFAULT_PAGE_NUMBER) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		if(pageSize < 1) {
			pageSize = DEFAULT_PAGE_LIMIT;
		}
		return PageRequest.of(pageNumber - 1, pageSize);
	}
}
