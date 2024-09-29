package com.huylhfx13483.jobsearchproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.huylhfx13483.jobsearchproject.entity.Company;
import com.huylhfx13483.jobsearchproject.entity.Recruitment;
import com.huylhfx13483.jobsearchproject.repository.RecruitmentRepository;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {

	@Autowired
	private RecruitmentRepository recruitmentRepository;
	
	@Override
	public List<Recruitment> findRecruitmentsByQuantityDesc() {
		List<Recruitment> tempRecruitments = recruitmentRepository.findAllOrderByQuantityDesc();
		List<Recruitment> recruitments = new ArrayList<>();
		
		int i = 1;
		for (Recruitment recruitment : tempRecruitments) {
			if (i > 3) {
				break;
			}
			recruitments.add(recruitment);
			i++;
		}
		return recruitments;
	}

	@Override
	public long countRecruitments() {
		
		return recruitmentRepository.count();
	}

	@Override
	public Page<Recruitment> findAllByCompany(Company company, int page, int size) {	
		Pageable pageable = PageRequest.of(page, size);
		
		return recruitmentRepository.findByCompany(company, pageable);
	}

	@Override
	public void save(Recruitment recruitment) {
		
		recruitmentRepository.save(recruitment);
	}

	@Override
	public Recruitment findById(Integer recruitmentId) {
		
		return recruitmentRepository.findById(recruitmentId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin tuyển dụng với id: " + recruitmentId));
	}

	@Override
	public void delete(Integer recruitmentId) {
		
		recruitmentRepository.deleteById(recruitmentId);
	}

	@Override
	public Page<Recruitment> searchByTitle(String keySearch, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		return recruitmentRepository.findByTitleContaining(keySearch, pageable);
	}

	@Override
	public Page<Recruitment> searchByCompany(String keySearch, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		return recruitmentRepository.findByCompanyCompanyNameContaining(keySearch, pageable);
	}

	@Override
	public Page<Recruitment> searchByAddress(String keySearch, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		return recruitmentRepository.findByAddressContaining(keySearch, pageable);
	}
	
}
