package com.psb.coding.phoneshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.psb.coding.phoneshop.entity.ProductImportHistory;

@Repository
public interface ProductImportHistoryRepository extends JpaRepository<ProductImportHistory, Long>, JpaSpecificationExecutor<ProductImportHistory>{

	List<ProductImportHistory> findByProductId(Long productId);
}
