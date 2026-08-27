package com.psb.coding.phoneshop.service.impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.psb.coding.phoneshop.entity.Brand;
import com.psb.coding.phoneshop.exception.ResourceNotFoundException;
import com.psb.coding.phoneshop.page.util.PageUtil;
import com.psb.coding.phoneshop.repository.BrandRepository;
import com.psb.coding.phoneshop.service.BrandService;
import com.psb.coding.phoneshop.specification.BrandFilter;
import com.psb.coding.phoneshop.specification.BrandSpec;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

	private final BrandRepository brandRepository;
	
	@Override
	public Brand create(Brand brand) {
		return brandRepository.save(brand);
	}
	
	@Override
	public Brand getById(Long id) {
		return brandRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Brand", id));
		/*Optional<Brand> brandOptional = brandRepository.findById(id);
		if(brandOptional.isPresent()) {
			return brandOptional.get();
		}
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Brand with id = %d not found".formatted(id));
		*/
	}

	@Override
	public Brand update(Brand brandUpdate) {
		Brand brand = getById(brandUpdate.getId());
		brand.setName(brandUpdate.getName()); //@TODO improve update
		return brandRepository.save(brand);
	}

	@Override
	public Brand delete(Long id) {
		Brand brand = getById(id);
		brandRepository.delete(brand);
		return brand;
	}

	@Override
	public Page<Brand> getBrands(Map<String, String> params) { // dynamic query + pagination
		BrandFilter brandFilter = new BrandFilter();
		if(params.containsKey("name")) {
			String name = params.get("name");
			brandFilter.setName(name);
		}
		if(params.containsKey("id")) {
			Long id = Long.parseLong(params.get("id"));
			brandFilter.setId(id);
		}
		
		Integer pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
		if(params.containsKey(PageUtil.PAGE_NUMBER)) {
			pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
		}
		Integer pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
		if(params.containsKey(PageUtil.PAGE_LIMIT)) {
			pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
		}
		
		BrandSpec brandSpec = new BrandSpec(brandFilter);
		
		Pageable pageable = PageUtil.pageRequest(pageNumber, pageLimit);
		
		Page<Brand> page = brandRepository.findAll(brandSpec, pageable);
		
		return page;
	}
	
	/*@Override
	public List<Brand> getBrands(Map<String, String> params) { // dynamic query
		BrandFilter brandFilter = new BrandFilter();
		if(params.containsKey("name")) {
			String name = params.get("name");
			brandFilter.setName(name);
		}
		if(params.containsKey("id")) {
			int id = Integer.parseInt(params.get("id"));
			brandFilter.setId(id);
		}
		BrandSpec brandSpec = new BrandSpec(brandFilter);
		List<Brand> listBrands = brandRepository.findAll(brandSpec);
		return listBrands;
	}*/

	/*@Override
	public List<Brand> getBrands(String name) { // get all brands
		List<Brand> list = brandRepository.findByNameLike("%" + name + "%");
		return list;
	}*/
}
