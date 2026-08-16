package com.psb.coding.phoneshop.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import com.psb.coding.phoneshop.entity.Sale;
import com.psb.coding.phoneshop.entity.SaleDetail;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
public class SaleDetailSpec implements Specification<SaleDetail> {
	
	private SaleDetailFilter saleDetailFilter;

	@Override
	public @Nullable Predicate toPredicate(Root<SaleDetail> saleDetail, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		Join<SaleDetail, Sale> sale = saleDetail.join("sale");
		if(Objects.nonNull(saleDetailFilter.getStartDate())) {
			Predicate startDate = criteriaBuilder.greaterThanOrEqualTo(sale.get("saleDate"), saleDetailFilter.getStartDate());
			predicates.add(startDate);
		}
		if(Objects.nonNull(saleDetailFilter.getEndDate())) {
			Predicate endDate = criteriaBuilder.lessThanOrEqualTo(sale.get("saleDate"), saleDetailFilter.getEndDate());
			predicates.add(endDate);
		}
		return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
	}

}
