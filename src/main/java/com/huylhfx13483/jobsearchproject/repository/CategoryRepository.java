package com.huylhfx13483.jobsearchproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.huylhfx13483.jobsearchproject.dto.CategoryDto;
import com.huylhfx13483.jobsearchproject.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	// Tim cac danh muc co so vi tri tuyen dung nhieu nhat (se ket hop voi dto va service de tim ra)
	// va so luong vi tri tuyen dung do
	@Query("SELECT new com.huylhfx13483.jobsearchproject.dto.CategoryDto(c.id, c.name, COUNT(r.id)) " +
			"FROM Category c JOIN c.recruitments r " +
			"GROUP BY c.id " +
			"ORDER BY COUNT(r.id) DESC")
	List<CategoryDto> findCategoriesWithMostRecruitment();
}
