package com.huylhfx13483.jobsearchproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huylhfx13483.jobsearchproject.entity.ApplyPost;
import com.huylhfx13483.jobsearchproject.repository.ApplyPostRepository;

@Service
public class ApplyPostServiceImpl implements ApplyPostService {

	@Autowired
	private ApplyPostRepository applyPostRepository;
	
	@Override
	public void save(ApplyPost applyPost) {
		
		applyPostRepository.save(applyPost);
	}

}
