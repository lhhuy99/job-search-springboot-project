package com.huylhfx13483.jobsearchproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.huylhfx13483.jobsearchproject.entity.Company;
import com.huylhfx13483.jobsearchproject.entity.Recruitment;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
	
	@Query("SELECT r FROM Recruitment r ORDER BY r.quantity DESC")
	List<Recruitment> findAllOrderByQuantityDesc();
	
	// Phuong thuc de lay danh sach bai dang tuyen dung theo phan trang cho mot cong ty
	Page<Recruitment> findByCompany(Company company, Pageable pageable);
	
	// Tim kiem theo tieu de
	Page<Recruitment> findByTitleContaining(String keySearch, Pageable pageable);	
	
	// Tim kiem theo cong ty
	Page<Recruitment> findByCompanyCompanyNameContaining(String keySearch, Pageable pageable);
	
	// Tim kiem theo dia chi
	Page<Recruitment> findByAddressContaining(String keySearch, Pageable pageable);
}
