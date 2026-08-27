package com.psb.coding.phoneshop.service;

import java.util.List;

import org.mapstruct.Named;

import com.psb.coding.phoneshop.dto.ModelDTO;
import com.psb.coding.phoneshop.entity.Model;

public interface ModelService {
	Model create(ModelDTO dto);
	
	@Named("getModelById")
	Model getById(Long id);
	
	List<Model> getModels();
	Model update(Long id, ModelDTO dto);
	Model delete(Long id);
	List<Model> getByBrand(Long id);
}
