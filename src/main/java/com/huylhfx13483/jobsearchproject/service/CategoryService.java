package com.huylhfx13483.jobsearchproject.service;

import java.util.List;

import com.huylhfx13483.jobsearchproject.dto.CategoryDto;
import com.huylhfx13483.jobsearchproject.entity.Category;

public interface CategoryService {
	
	public List<CategoryDto> findCategoriesWithMostRecruitment();
	
	public List<Category> findAll();
	
	public Category findById(Integer categoryId);
}
