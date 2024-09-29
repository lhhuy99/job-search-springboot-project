package com.huylhfx13483.jobsearchproject.service;

import com.huylhfx13483.jobsearchproject.entity.User;

public interface UserService {
	public void save(User user);
	
	public User findByEmail(String email);
	
	public long countByRole(String roleName);
}
