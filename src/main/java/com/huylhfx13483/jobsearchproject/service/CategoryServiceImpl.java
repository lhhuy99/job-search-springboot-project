package com.huylhfx13483.jobsearchproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huylhfx13483.jobsearchproject.dto.CategoryDto;
import com.huylhfx13483.jobsearchproject.entity.Category;
import com.huylhfx13483.jobsearchproject.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> findCategoriesWithMostRecruitment() {
		List<CategoryDto> result = categoryRepository.findCategoriesWithMostRecruitment();
		List<CategoryDto> tempList = new ArrayList<>();
		
		int i = 1;
		for (CategoryDto categoryDto : result) {
			if (i > 4) {
				break;
			}
		
			tempList.add(categoryDto);
			i++;
		}
		
		return tempList;
	}

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category findById(Integer categoryId) {
		
		return categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với id: " + categoryId));
	}
	
}
