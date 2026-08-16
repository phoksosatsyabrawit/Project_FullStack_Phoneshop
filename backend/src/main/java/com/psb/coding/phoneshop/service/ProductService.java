package com.psb.coding.phoneshop.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.psb.coding.phoneshop.dto.PriceDTO;
import com.psb.coding.phoneshop.dto.ProductDTO;
import com.psb.coding.phoneshop.dto.ProductImportHistoryDTO;
import com.psb.coding.phoneshop.entity.Product;

public interface ProductService {

	Product creat(Product product);
	Product getById(Long id);
	List<ProductDTO> getProducts();
	void imports(ProductImportHistoryDTO productHistoryDto);
	Product setSalePrice(Long id, PriceDTO priceDto);
	Map<Integer, String> upload(MultipartFile file);
	Product getByModelIdAndColorId(Long modelId, Long colorId);
}
