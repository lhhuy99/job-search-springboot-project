package com.huylhfx13483.jobsearchproject.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huylhfx13483.jobsearchproject.entity.Company;
import com.huylhfx13483.jobsearchproject.entity.User;
import com.huylhfx13483.jobsearchproject.service.CompanyService;
import com.huylhfx13483.jobsearchproject.service.UserService;

@Controller
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;

	@GetMapping("/showDetailCompany/{companyId}")
	public String showDetailCompany(@PathVariable("companyId") Integer companyId, Principal principal, Model model) {
		
		// Lay ra User da dang nhap thong qua email
		User userLoggedIn = getAuthenticatedUser(principal);
		
		// Lay Company tu companyid
		Company company = companyService.findById(companyId);
		
		// add vao model
		model.addAttribute("userLoggedIn", userLoggedIn);
		model.addAttribute("company", company);
		
		
		return "public/detail-company";
	}
	
	private User getAuthenticatedUser(Principal principal) {
		// Lay email tu nguoi dung dang nhap
		String email = principal.getName();

		// Lay ra user da dang nhap thong qua email
		return userService.findByEmail(email);
	}
}
