package com.psb.coding.phoneshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psb.coding.phoneshop.entity.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
	List<Model> findByBrandId(Long brandId);
}
