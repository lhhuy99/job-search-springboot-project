package com.huylhfx13483.jobsearchproject.service;

import com.huylhfx13483.jobsearchproject.dto.CompanyDto;
import com.huylhfx13483.jobsearchproject.entity.Company;

public interface CompanyService {
	
	public CompanyDto findCompanyWithMostRecruitments();
	
	public long countCompanies();
	
	public void save(Company company);
	
	public Company findById(Integer companyId);
}
