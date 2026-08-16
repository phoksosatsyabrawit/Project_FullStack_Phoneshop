package com.psb.coding.phoneshop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psb.coding.phoneshop.dto.ModelDTO;
import com.psb.coding.phoneshop.entity.Model;
import com.psb.coding.phoneshop.mapper.ModelMapper;
import com.psb.coding.phoneshop.service.ModelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/models")
public class ModelController { // inject dependency through constructor

	private final ModelService modelService;
	private final ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<?> createModel(@RequestBody ModelDTO modelDTO){
		/*Model model = modelService.save(modelMapper.toModel(modelDTO));*/
		Model model = modelService.save(modelDTO);
		return ResponseEntity.ok(modelMapper.toModelDTO(model));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		Model modelId = modelService.getById(id);
		return ResponseEntity.ok(modelMapper.toModelDTO(modelId));
	}
	
	@GetMapping
	public ResponseEntity<?> getModels(){
		List<Model> models = modelService.getModels();
		List<ModelDTO> listModels = models.stream()
		.map(modelMapper::toModelDTO)
		.toList();
		return ResponseEntity.ok(listModels);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateModel(@PathVariable Long id, @RequestBody ModelDTO dto){
		Model update = modelService.update(id, dto);
		return ResponseEntity.ok(modelMapper.toModelDTO(update));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteModel(@PathVariable Long id){
		Model delete = modelService.delete(id);
		return ResponseEntity.ok(delete);
	}
}
