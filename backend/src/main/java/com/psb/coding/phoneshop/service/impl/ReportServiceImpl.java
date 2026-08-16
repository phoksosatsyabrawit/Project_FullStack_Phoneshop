package com.psb.coding.phoneshop.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.psb.coding.phoneshop.dto.report.ExpenseReportDTO;
import com.psb.coding.phoneshop.dto.report.ProductReportDTO;
import com.psb.coding.phoneshop.entity.Product;
import com.psb.coding.phoneshop.entity.ProductImportHistory;
import com.psb.coding.phoneshop.entity.SaleDetail;
import com.psb.coding.phoneshop.projection.ProductSale;
import com.psb.coding.phoneshop.repository.ProductImportHistoryRepository;
import com.psb.coding.phoneshop.repository.ProductRepository;
import com.psb.coding.phoneshop.repository.SaleDetailRepository;
import com.psb.coding.phoneshop.repository.SaleRepository;
import com.psb.coding.phoneshop.service.ReportService;
import com.psb.coding.phoneshop.specification.ProductImportHistoryFilter;
import com.psb.coding.phoneshop.specification.ProductImportHistorySpec;
import com.psb.coding.phoneshop.specification.SaleDetailFilter;
import com.psb.coding.phoneshop.specification.SaleDetailSpec;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

	private final SaleRepository saleRepository;
	private final SaleDetailRepository saleDetailRepository;
	private final ProductImportHistoryRepository productImportHistoryRepository;
	private final ProductRepository productRepository;

	@Override
	public List<ProductSale> getProductSale(LocalDate startDate, LocalDate endDate) {
		LocalDateTime start = startDate.atStartOfDay();
		LocalDateTime end = endDate.atTime(LocalTime.MAX);
		List<ProductSale> productSales = saleRepository.findProductSale(start, end);
		return productSales;
	}

	@Override
	public List<ProductReportDTO> getProductReport(LocalDate startDate, LocalDate endDate) {
		List<ProductReportDTO> productReports = new ArrayList<>();
		LocalDateTime start = startDate.atStartOfDay();
		LocalDateTime end = endDate.atTime(LocalTime.MAX);
		SaleDetailFilter detailFilter = new SaleDetailFilter();
		detailFilter.setStartDate(start);
		detailFilter.setEndDate(end);
		Specification<SaleDetail> spec = new SaleDetailSpec(detailFilter);
		// group by product
		List<SaleDetail> sdList = saleDetailRepository.findAll(spec);
		Map<Product, List<SaleDetail>> sdMap = sdList.stream().collect(Collectors.groupingBy(SaleDetail::getProduct));
		// product map
		List<Long> productIds = sdList.stream().map(sd -> sd.getProduct().getId()).toList();
		Map<Long, Product> pMap = productRepository.findAllById(productIds).stream()
				.collect(Collectors.toMap(Product::getId, Function.identity()));
		for (var entry : sdMap.entrySet()) {
			Product product = pMap.get(entry.getKey().getId());
			List<SaleDetail> sdLists = entry.getValue();
			Integer unit = sdLists.stream().map(sd -> sd.getUnit()).reduce(0, (a, b) -> a + b);
			Double total = sdLists.stream().map(sd -> sd.getUnit() * sd.getAmount().doubleValue())
					.reduce(0d,(a, b) -> a + b);
			ProductReportDTO reportDTO = new ProductReportDTO();
			reportDTO.setProductId(product.getId());
			reportDTO.setProductName(product.getName());
			reportDTO.setUnit(unit);
			reportDTO.setTotal(BigDecimal.valueOf(total));
			productReports.add(reportDTO);
		}
		return productReports;
	}

	@Override
	public List<ExpenseReportDTO> getExpenseReport(LocalDate startDate, LocalDate endDate) {
		LocalDateTime start = startDate.atStartOfDay();
		LocalDateTime end = endDate.atTime(LocalTime.MAX);
		ProductImportHistoryFilter importHistoryFilter = new ProductImportHistoryFilter();
		importHistoryFilter.setStartDate(start);
		importHistoryFilter.setEndDate(end);
		Specification<ProductImportHistory> importHistSpec = new ProductImportHistorySpec(importHistoryFilter);
		// group by product
		Map<Product, List<ProductImportHistory>> piMap = productImportHistoryRepository.findAll(importHistSpec).stream()
				.collect(Collectors.groupingBy(ProductImportHistory::getProduct));
		// product list
		List<Long> productIds = productImportHistoryRepository.findAll(importHistSpec).stream()
				.map(pi -> pi.getProduct().getId()).toList();
		Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
				.collect(Collectors.toMap(Product::getId, Function.identity()));
		var expenseReportDtos = new ArrayList<ExpenseReportDTO>();
		for(var entry : piMap.entrySet()) {
			Product product = productMap.get(entry.getKey().getId());
			List<ProductImportHistory> piList = entry.getValue();
			int unit = piList.stream()
					.mapToInt(pi -> pi.getImportUnit()).sum();
			double amount = piList.stream()
					.mapToDouble(pi -> pi.getImportUnit() * pi.getPricePerUnit().doubleValue()).sum();
			var expenseReportDto = new ExpenseReportDTO();
			expenseReportDto.setProductId(product.getId());
			expenseReportDto.setProductName(product.getName());
			expenseReportDto.setUnit(unit);
			expenseReportDto.setTotal(BigDecimal.valueOf(amount));
			expenseReportDtos.add(expenseReportDto);
		}
		Collections.sort(expenseReportDtos, (a,b) -> (int) (a.getProductId() - b.getProductId()));
		return expenseReportDtos;
	}
}
