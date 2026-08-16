package com.psb.coding.phoneshop.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psb.coding.phoneshop.dto.report.ExpenseReportDTO;
import com.psb.coding.phoneshop.dto.report.ProductReportDTO;
import com.psb.coding.phoneshop.projection.ProductSale;
import com.psb.coding.phoneshop.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
	
	private final ReportService reportService;

	@GetMapping("/productSales")	
	public ResponseEntity<?> getProductSale(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
		List<ProductSale> productSales = reportService.getProductSale(startDate, endDate);
		return ResponseEntity.ok(productSales);
	}
	
	@GetMapping("/productReports")
	public ResponseEntity<?> getProductReport(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
		List<ProductReportDTO> productReport = reportService.getProductReport(startDate, endDate);
		return ResponseEntity.ok(productReport);
	}
	
	@GetMapping("/expenses")
	public ResponseEntity<?> expenseReport(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
		List<ExpenseReportDTO> expenseReport = reportService.getExpenseReport(startDate, endDate);
		return ResponseEntity.ok(expenseReport);
	}
}
