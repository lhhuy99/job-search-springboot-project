package com.huylhfx13483.jobsearchproject.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.huylhfx13483.jobsearchproject.entity.ApplyPost;
import com.huylhfx13483.jobsearchproject.entity.Recruitment;
import com.huylhfx13483.jobsearchproject.entity.User;
import com.huylhfx13483.jobsearchproject.service.ApplyPostService;
import com.huylhfx13483.jobsearchproject.service.FileService;
import com.huylhfx13483.jobsearchproject.service.RecruitmentService;
import com.huylhfx13483.jobsearchproject.service.UserService;

@Controller
public class ApplyController {

	@Autowired
	private UserService userService;

	@Autowired
	private RecruitmentService recruitmentService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private ApplyPostService applyPostService;

	@PostMapping("/applyJob/{recruitmentId}")
	public String applyJob(@RequestParam("file") MultipartFile file, @RequestParam("cvOption") String cvOption,
			@RequestParam("description") String description, @PathVariable("recruitmentId") Integer recruitmentId,
			Principal principal) {

		User user = getAuthenticatedUser(principal);
		Recruitment recruitment = recruitmentService.findById(recruitmentId);

		ApplyPost applyPost = new ApplyPost();
		applyPost.setUser(user);
		applyPost.setRecruitment(recruitment);

		// Dinh dang ngay thang theo MM/DD/YYYY cho ngay khoi tao createdAt
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate nowDate = LocalDate.now();

		applyPost.setCreatedAt(nowDate.format(outputFormatter));

		if ("1".equals(cvOption)) {
			// Nguoi dung chon CV da luu
			applyPost.setNewCvFile(null);
			applyPost.setCv(user.getCv());
			applyPost.setText(description);
		} else if ("2".equals(cvOption) && !file.isEmpty()) {
			// Nguoi dung chon upload CV moi
			String newCvFileName = fileService.saveFile(file, "cv");

			if (newCvFileName != null) {
				applyPost.setNewCvFile(newCvFileName);
			}
			
			applyPost.setText(description);
		}
		
		applyPostService.save(applyPost);
		
		return "redirect:/homePage/home";
	}

	public User getAuthenticatedUser(Principal principal) {
		// Lay email tu nguoi dung dang nhap
		String email = principal.getName();

		// Lay ra user da dang nhap thong qua email
		return userService.findByEmail(email);
	}
}
