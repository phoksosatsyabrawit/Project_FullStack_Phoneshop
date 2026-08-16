package com.psb.coding.phoneshop.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import com.psb.coding.phoneshop.entity.ProductImportHistory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
public class ProductImportHistorySpec implements Specification<ProductImportHistory> {
	
	private ProductImportHistoryFilter importHistoryFilter;

	@Override
	public @Nullable Predicate toPredicate(Root<ProductImportHistory> importHis, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if(Objects.nonNull(importHistoryFilter.getStartDate())) {
			Predicate startDate = criteriaBuilder.greaterThanOrEqualTo(importHis.get("importDate"), importHistoryFilter.getStartDate());
			predicates.add(startDate);
		}
		if(Objects.nonNull(importHistoryFilter.getEndDate())) {
			Predicate endDate = criteriaBuilder.lessThanOrEqualTo(importHis.get("importDate"), importHistoryFilter.getEndDate());
			predicates.add(endDate);
		}
		return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
	}

}
