package com.huylhfx13483.jobsearchproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huylhfx13483.jobsearchproject.dto.CompanyDto;
import com.huylhfx13483.jobsearchproject.entity.Company;
import com.huylhfx13483.jobsearchproject.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public CompanyDto findCompanyWithMostRecruitments() {
		List<CompanyDto> result = companyRepository.findCompaniesWithMostRecruitments();
		
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public long countCompanies() {
		
		return companyRepository.count();
	}

	@Override
	public void save(Company company) {
		
		companyRepository.save(company);
	}

	@Override
	public Company findById(Integer companyId) {
	    return companyRepository.findById(companyId)
	            .orElseThrow(() -> new RuntimeException("Không tìm thấy công ty với id: " + companyId));
	}
	
}
