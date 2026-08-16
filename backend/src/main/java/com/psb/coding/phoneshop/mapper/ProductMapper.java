package com.psb.coding.phoneshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.psb.coding.phoneshop.dto.ProductDTO;
import com.psb.coding.phoneshop.dto.ProductImportHistoryDTO;
import com.psb.coding.phoneshop.entity.Product;
import com.psb.coding.phoneshop.entity.ProductImportHistory;
import com.psb.coding.phoneshop.service.ColorService;
import com.psb.coding.phoneshop.service.ModelService;

@Mapper(componentModel = "spring", uses = {ModelService.class, ColorService.class})
public interface ProductMapper {

	@Mapping(target = "model", source = "modelId", qualifiedByName = "getModelById")
	@Mapping(target = "color", source = "colorId", qualifiedByName = "getColorById")
	Product toProduct(ProductDTO productDTO);
	
	@Mapping(target = "modelId", source = "model.id")
	@Mapping(target = "colorId", source = "color.id")
	ProductDTO toProductDto(Product product);
	
	@Mapping(target = "product.id", source = "productId")
	ProductImportHistory toProductImportHistory(ProductImportHistoryDTO productHistoryDto);
}
