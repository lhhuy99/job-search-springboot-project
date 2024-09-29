package com.huylhfx13483.jobsearchproject.service;

import com.huylhfx13483.jobsearchproject.entity.Role;

public interface RoleService {
	public Role findByRoleName(String roleName);
	public void saveRole(Role role);
}
