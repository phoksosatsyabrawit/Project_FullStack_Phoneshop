package com.psb.coding.phoneshop.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.psb.coding.phoneshop.dto.PriceDTO;
import com.psb.coding.phoneshop.dto.ProductDTO;
import com.psb.coding.phoneshop.dto.ProductImportHistoryDTO;
import com.psb.coding.phoneshop.entity.Product;
import com.psb.coding.phoneshop.entity.ProductImportHistory;
import com.psb.coding.phoneshop.exception.ApiException;
import com.psb.coding.phoneshop.exception.ResourceNotFoundException;
import com.psb.coding.phoneshop.mapper.ProductMapper;
import com.psb.coding.phoneshop.repository.ProductImportHistoryRepository;
import com.psb.coding.phoneshop.repository.ProductRepository;
import com.psb.coding.phoneshop.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductImportHistoryRepository productImportHistoryRepository;
	private final ProductMapper productMapper;

	@Override
	public Product creat(Product product) {
		String productName = "%s %s".formatted(product.getModel().getName(), product.getColor().getName());
		product.setName(productName);
		return productRepository.save(product);
	}

	@Override
	public Product getById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", id));
	}

	@Override
	public List<ProductDTO> getProducts() {
		List<ProductDTO> productList = productRepository.findAll().stream()
				.map(product -> productMapper.toProductDto(product)).toList();
		return productList;
	}

	@Override
	public void imports(ProductImportHistoryDTO productHistoryDto) {
		// save & update product available_unit
		Product product = getById(productHistoryDto.getProductId());
		Integer availableUnit = 0;
		if (product.getAvailableUnit() != null) {
			availableUnit = product.getAvailableUnit();
		}
		product.setAvailableUnit(availableUnit + productHistoryDto.getImportUnit());
		productRepository.save(product);
		// save import history
		ProductImportHistory importProduct = productMapper.toProductImportHistory(productHistoryDto);
		productImportHistoryRepository.save(importProduct);
	}

	@Override
	public Product setSalePrice(Long id, PriceDTO priceDto) {
		Product product = getById(id);
		
		// validate price before set
		List<ProductImportHistory> productImports = productImportHistoryRepository.findByProductId(product.getId());
		productImports.forEach(pi -> {
			if(priceDto.getSalePrice().compareTo(pi.getPricePerUnit()) < 0) {
				String price = "Invalid price %.2f";
				throw new ApiException(HttpStatus.BAD_REQUEST, price.formatted(priceDto.getSalePrice()));
			}
		});
		
		product.setSalePrice(priceDto.getSalePrice());
		return productRepository.save(product);
	}

	@Override
	public Map<Integer, String> upload(MultipartFile file) {
		Map<Integer, String> map = new HashMap<>();
		try {
			Workbook workBook = new XSSFWorkbook(file.getInputStream());
			Sheet sheet = workBook.getSheet("importProductV1");
			Iterator<Row> rowIterator = sheet.iterator();
			// skip header
			if (rowIterator.hasNext()) {
				rowIterator.next();
			}
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Integer rowNumber = row.getRowNum();
				try {
					Integer cellIndex = 0;

					Cell cellNo = row.getCell(cellIndex++);
					rowNumber = (int) cellNo.getNumericCellValue();
					Cell cellModelId = row.getCell(cellIndex++);
					Long modelId = (long) cellModelId.getNumericCellValue();
					Cell cellColorID = row.getCell(cellIndex++);
					Long colorId = (long) cellColorID.getNumericCellValue();
					Cell cellImportPrice = row.getCell(cellIndex++);
					Double importPrice = (double) cellImportPrice.getNumericCellValue();
					Cell cellImportUnit = row.getCell(cellIndex++);
					Integer importUnit = (int) cellImportUnit.getNumericCellValue();
					Cell cellImportDate = row.getCell(cellIndex++);
					LocalDateTime importDate = cellImportDate.getLocalDateTimeCellValue();

					// Validation
					if (importUnit < 1) {
						String unit = "Quantity [%d] is Invalid.";
						throw new ApiException(HttpStatus.BAD_REQUEST, unit.formatted(importUnit));
					}
					if (importPrice < 1) {
						String price = "Incorrect price [%.2f%].";
						throw new ApiException(HttpStatus.BAD_REQUEST, price.formatted(importPrice));
					}
					if (importDate == null) {
						throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid date.");
					}
					Product products = getByModelIdAndColorId(modelId, colorId);

					// save & update product available_unit
					Integer availableUnit = 0;
					if (products.getAvailableUnit() != null) {
						availableUnit = products.getAvailableUnit();
					}
					products.setAvailableUnit(availableUnit + importUnit);
					productRepository.save(products);

					ProductImportHistory importHistory = new ProductImportHistory();
					importHistory.setImportUnit(importUnit);
					importHistory.setPricePerUnit(BigDecimal.valueOf(importPrice));
					importHistory.setImportDate(importDate);
					importHistory.setProduct(products);
					productImportHistoryRepository.save(importHistory);
				} catch (ApiException e) {
					map.put(rowNumber, e.getMessage());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Product getByModelIdAndColorId(Long modelId, Long colorId) {
		String errorMessage = "[Product] with model %d and color %d not available.";
		return productRepository.findByModelIdAndColorId(modelId, colorId)
				.orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, errorMessage.formatted(modelId, colorId)));
	}
}
