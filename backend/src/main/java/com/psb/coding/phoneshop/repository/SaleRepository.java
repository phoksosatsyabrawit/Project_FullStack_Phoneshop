package com.psb.coding.phoneshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psb.coding.phoneshop.entity.Sale;
import com.psb.coding.phoneshop.projection.ProductSale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "select p.product_id productId, p.product_name productName, sum(sd.unit) unit, sum(sd.unit*sd.amount) Total from sale_detail sd \r\n"
			+ "inner join sales s on s.sale_id = sd.sale_id \r\n"
			+ "inner join products p on p.product_id = sd.product_id \r\n"
			+ "where status(s.is_active = true or s.is_active is null) or date(s.sale_date) >= :startDate and date(s.sale_date) <= :endDate \r\n"
			+ "group by p.product_id", nativeQuery = true)
	List<ProductSale> findProductSale(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
