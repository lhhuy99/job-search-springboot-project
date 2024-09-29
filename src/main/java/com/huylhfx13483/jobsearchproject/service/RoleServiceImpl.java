package com.huylhfx13483.jobsearchproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huylhfx13483.jobsearchproject.entity.Role;
import com.huylhfx13483.jobsearchproject.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role findByRoleName(String roleName) {
		
		return roleRepository.findByRoleName(roleName);
	}

	@Override
	public void saveRole(Role role) {
		
		roleRepository.save(role);
	}
}
