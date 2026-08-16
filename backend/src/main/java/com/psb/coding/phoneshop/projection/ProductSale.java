package com.psb.coding.phoneshop.projection;

import java.math.BigDecimal;

public interface ProductSale { // spring data handle mapping implicitly

	Long getProductId();
	String getProductName();
	Integer getUnit();
	BigDecimal getTotal();
}
