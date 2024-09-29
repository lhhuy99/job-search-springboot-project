package com.huylhfx13483.jobsearchproject.dto;

public class CategoryDto {
	private Integer categoryId;
	private String categoryName;
	private Long recruitmentCount;
	
	public CategoryDto() {
		
	}
	
	public CategoryDto(Integer categoryId, String categoryName, Long recruitmentCount) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.recruitmentCount = recruitmentCount;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getRecruitmentCount() {
		return recruitmentCount;
	}

	public void setRecruitmentCount(Long recruitmentCount) {
		this.recruitmentCount = recruitmentCount;
	}
	
}
