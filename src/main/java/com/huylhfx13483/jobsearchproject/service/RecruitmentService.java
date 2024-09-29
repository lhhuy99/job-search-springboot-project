package com.huylhfx13483.jobsearchproject.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.huylhfx13483.jobsearchproject.entity.Company;
import com.huylhfx13483.jobsearchproject.entity.Recruitment;

public interface RecruitmentService {
	
	public List<Recruitment> findRecruitmentsByQuantityDesc();
	
	public long countRecruitments();
	
	public Page<Recruitment> findAllByCompany(Company company, int page, int size);
	
	public void save(Recruitment recruitment);
	
	public Recruitment findById(Integer recruitmentId);
	
	public void delete(Integer recruitmentId);
	
	public Page<Recruitment> searchByTitle(String keySearch, int page, int size);
	
	public Page<Recruitment> searchByCompany(String keySearch, int page, int size);
	
	public Page<Recruitment> searchByAddress(String keySearch, int page, int size);
}
