package com.psb.coding.phoneshop.service;

import com.psb.coding.phoneshop.dto.SaleDTO;
import com.psb.coding.phoneshop.entity.Sale;

public interface SaleService {

	void sale(SaleDTO saleDto);

	Sale getById(Long saleId);

	void voidSale(Long saleId);
}
