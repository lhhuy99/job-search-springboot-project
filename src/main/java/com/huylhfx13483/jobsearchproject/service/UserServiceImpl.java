package com.huylhfx13483.jobsearchproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huylhfx13483.jobsearchproject.entity.User;
import com.huylhfx13483.jobsearchproject.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public void save(User user) {
		
        userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}

	@Override
	public long countByRole(String roleName) {
		
		return userRepository.countByRoleRoleName(roleName);
	}
}
