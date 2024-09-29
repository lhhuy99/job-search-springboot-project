package com.huylhfx13483.jobsearchproject.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.huylhfx13483.jobsearchproject.entity.Cv;
import com.huylhfx13483.jobsearchproject.repository.CvRepository;

public class CvServiceImpl implements CvService {
	
	@Autowired
	private CvRepository cvRepository;

	@Override
	public void save(Cv cv) {
		
		cvRepository.save(cv);
	}

}
