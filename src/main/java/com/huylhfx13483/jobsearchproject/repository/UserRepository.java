package com.huylhfx13483.jobsearchproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huylhfx13483.jobsearchproject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmail(String email);
	
	// Tim tong cac user dua theo role
	// Role dau la role trong User
	// RoleName laf roleName trong Role
	long countByRoleRoleName(String roleName);
}
