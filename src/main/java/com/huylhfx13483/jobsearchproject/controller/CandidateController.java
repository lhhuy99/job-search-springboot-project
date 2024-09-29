package com.huylhfx13483.jobsearchproject.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huylhfx13483.jobsearchproject.entity.Company;
import com.huylhfx13483.jobsearchproject.entity.Recruitment;
import com.huylhfx13483.jobsearchproject.entity.User;
import com.huylhfx13483.jobsearchproject.service.RecruitmentService;
import com.huylhfx13483.jobsearchproject.service.UserService;

@Controller
public class CandidateController {

	@Autowired
	private UserService userService;

	@Autowired
	private RecruitmentService recruitmentService;

	@GetMapping("/showCandidates")
	public String showCandidates(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Principal principal, Model model) {

		// Lay email tu nguoi dung da dang nhap
		String email = principal.getName();
		// Lay ra User da dang nhap thong qua email
		User userLoggedIn = userService.findByEmail(email);

		Company company = userLoggedIn.getCompany();

		Page<Recruitment> recruitmentPage = recruitmentService.findAllByCompany(company, page, size);
		
		model.addAttribute("recruitments", recruitmentPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", recruitmentPage.getTotalPages());
		model.addAttribute("userLoggedIn", userLoggedIn);

		return "public/list-user";
	}
}
