package com.psb.coding.phoneshop.service;

import java.util.List;

import org.mapstruct.Named;

import com.psb.coding.phoneshop.dto.ModelDTO;
import com.psb.coding.phoneshop.entity.Model;

public interface ModelService {
	Model save(ModelDTO modelDTO);
	
	@Named("getModelById")
	Model getById(Long id);
	
	List<Model> getModels();
	Model update(Long id, ModelDTO modelUpdate);
	Model delete(Long id);
	List<Model> getByBrand(Long id);
}
