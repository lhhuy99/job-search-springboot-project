package com.huylhfx13483.jobsearchproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huylhfx13483.jobsearchproject.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRoleName(String roleName);
}
