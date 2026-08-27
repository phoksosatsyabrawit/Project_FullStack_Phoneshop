package com.psb.coding.phoneshop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.psb.coding.phoneshop.dto.SaleDTO;
import com.psb.coding.phoneshop.dto.report.ProductSaleDTO;
import com.psb.coding.phoneshop.entity.Product;
import com.psb.coding.phoneshop.entity.Sale;
import com.psb.coding.phoneshop.entity.SaleDetail;
import com.psb.coding.phoneshop.exception.ApiException;
import com.psb.coding.phoneshop.exception.ResourceNotFoundException;
import com.psb.coding.phoneshop.repository.ProductRepository;
import com.psb.coding.phoneshop.repository.SaleDetailRepository;
import com.psb.coding.phoneshop.repository.SaleRepository;
import com.psb.coding.phoneshop.service.ProductService;
import com.psb.coding.phoneshop.service.SaleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SaleServiceImpl implements SaleService {

	private final SaleRepository saleRepository;
	private final SaleDetailRepository saleDetailRepository;
	private final ProductService productService;
	private final ProductRepository productRepository;

	@Override
	public void sale(SaleDTO saleDto) {
		// validate product
		List<Long> productSaleDtoId = saleDto.getProducts().stream().map(ProductSaleDTO::getProductId).toList();
		productSaleDtoId.forEach(productService::getById);
		// validate stock
		List<Product> products = productRepository.findAllById(productSaleDtoId);
		Map<Long, Product> productMap = products.stream()
				.collect(Collectors.toMap(Product::getId, Function.identity()));
		saleDto.getProducts().forEach(ps -> {
			Product product = productMap.get(ps.getProductId());
			Integer availableUnit = 0;
			if (product.getAvailableUnit() != null) {
				availableUnit = product.getAvailableUnit();
			}
			if (availableUnit < ps.getSaleUnit()) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "%s not available.".formatted(product.getName()));
			}

			// update stock
			product.setAvailableUnit(product.getAvailableUnit() - ps.getSaleUnit());
			productRepository.save(product);
		});
		// save sale
		Sale sale = new Sale();
		sale.setSaleDate(saleDto.getSaleDate());
		saleRepository.save(sale);
		// save sale_detail
		saleDto.getProducts().forEach(ps -> {
			Product product = productMap.get(ps.getProductId());

			SaleDetail saleDetail = new SaleDetail();
			saleDetail.setUnit(ps.getSaleUnit());
			saleDetail.setAmount(product.getSalePrice());
			saleDetail.setProduct(product);
			saleDetail.setSale(sale);
			saleDetailRepository.save(saleDetail);
		});
	}

	@Override
	public Sale getById(Long saleId) {
		return saleRepository.findById(saleId).orElseThrow(() -> new ResourceNotFoundException("Sale", saleId));
	}

	@Override
	public void voidSale(Long saleId) {
		// change status
		Sale sale = getById(saleId);
		sale.setIsActive(false);
		saleRepository.save(sale);

		// update stock
		List<SaleDetail> saleDetails = saleDetailRepository.findBySaleId(saleId);
		List<Long> productIds = saleDetails.stream().map(sd -> sd.getProduct().getId()).toList();
		productIds.stream().forEach(productService::getById);
		
		List<Product> products = productRepository.findAllById(productIds);
		Map<Long, Product> productMap = products.stream()
				.collect(Collectors.toMap(Product::getId, Function.identity()));

		saleDetails.forEach(sd -> {
			Product product = productMap.get(sd.getProduct().getId());
			product.setAvailableUnit(product.getAvailableUnit() + sd.getUnit());
			productRepository.save(product);
		});
	}

}
