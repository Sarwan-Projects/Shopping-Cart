package com.ecom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ecom.model.Category;
import com.ecom.repository.CategoryRepo;
import com.ecom.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService
{
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public Category saveCategory(Category category) 
	{
		return categoryRepo.save(category);
	}

	@Override
	public List<Category> getAllCategory() 
	{
		return categoryRepo.findAll();
	}

	@Override
	public boolean existCategory(String name) 
	{
		return categoryRepo.existsByName(name);
	}

	@Override
	public boolean deleteCategory(int id) 
	{
		Category else1 = categoryRepo.findById(id).orElse(null);
		if(!ObjectUtils.isEmpty(else1))
		{
			categoryRepo.delete(else1);
			return true;
		}
		return false;
	}

	@Override
	public Category getCategoryById(int id) 
	{
		Category orElse = categoryRepo.findById(id).orElse(null);
		return orElse;
	}

	@Override
	public List<Category> getAllActiveCategory() 
	{
		List<Category> activeTrue = categoryRepo.findByIsActiveTrue();
		return activeTrue;
	}

	@Override
	public Page<Category> getAllCategoryPagination(Integer pageNo, Integer pageSize) 
	{
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return categoryRepo.findAll(pageable);
	}

	
}