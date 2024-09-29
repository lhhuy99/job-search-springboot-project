package com.huylhfx13483.jobsearchproject.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huylhfx13483.jobsearchproject.dto.CategoryDto;
import com.huylhfx13483.jobsearchproject.dto.CompanyDto;
import com.huylhfx13483.jobsearchproject.entity.Company;
import com.huylhfx13483.jobsearchproject.entity.Recruitment;
import com.huylhfx13483.jobsearchproject.entity.User;
import com.huylhfx13483.jobsearchproject.service.CategoryService;
import com.huylhfx13483.jobsearchproject.service.CompanyService;
import com.huylhfx13483.jobsearchproject.service.RecruitmentService;
import com.huylhfx13483.jobsearchproject.service.UserService;

@Controller
@RequestMapping("/homePage")
public class HomeController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private RecruitmentService recruitmentService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@GetMapping("/home")
	public String showHomePage(Model theModel, Principal principal) {

		// Neu nguoi dung da dang nhap thi lay ra user va them vao model
		User userLoggedIn = null;

		if (principal != null) {
			// Lay email tu nguoi dung da dang nhap
			String email = principal.getName();
			// Lay ra User da dang nhap thong qua email
			userLoggedIn = userService.findByEmail(email);

			// Khoi tao company cho tai khoan co role la Employer vua khoi tao chua co company
			if (userLoggedIn.getRole().getId() == 1 && userLoggedIn.getCompany() == null) {
				Company company = new Company();
				userLoggedIn.setCompany(company);
				userService.save(userLoggedIn);
			}

			// Them user da dang nhap vao model
			theModel.addAttribute("userLoggedIn", userLoggedIn);
		}

		// Tim ra cong ty co vi tri ung tuyen nhieu nhat(so luong recruitment) va luu
		// vao dto
		// CompanyRecruitmentCount
		CompanyDto companyDto = companyService.findCompanyWithMostRecruitments();

		// Tim ra cac danh muc co vi tri ung tuyen(so luong recruitment) nhieu nhat
		List<CategoryDto> categoryDtoList = categoryService.findCategoriesWithMostRecruitment();

		// Tim 3 viec lam co so luong(quantity) nhieu nhat
		List<Recruitment> recruitments = recruitmentService.findRecruitmentsByQuantityDesc();

		// Tim tong so user voi vai tro la nguoi tim viec
		long numberCandidate = userService.countByRole("ROLE_JOBSEEKER");

		// Tim tong so cong ty
		long numberCompany = companyService.countCompanies();

		// Tim tong so vi tri tuyen dung(tong so luong recruitment)
		long numberRecruitment = recruitmentService.countRecruitments();

		// Them vao model
		// Them cac attribute lien quan den company vao model
		if (companyDto != null) {
			theModel.addAttribute("companyName", companyDto.getCompanyName());
			theModel.addAttribute("logoUrl", companyDto.getLogo());
			theModel.addAttribute("recruitmentCount", companyDto.getRecruitmentCount());
			Company company = companyService.findById(companyDto.getCompanyId());
			theModel.addAttribute("company", company);
		} else {
			theModel.addAttribute("message1", "Không có công ty nào có sẵn");
		}

		// Them cac attribute lien quan den category vao model
		if (categoryDtoList != null && !categoryDtoList.isEmpty()) {
			theModel.addAttribute("categories", categoryDtoList);
		} else {
			theModel.addAttribute("message2", "Không có danh mục nào có sẵn");
		}

		// Them cac attribute lien quan den recruitment vao model
		if (recruitments != null && !recruitments.isEmpty()) {
			theModel.addAttribute("recruitments", recruitments);
		} else {
			theModel.addAttribute("message3", "Không có tuyển dụng nào có sẵn");
		}

		theModel.addAttribute("numberCandidate", numberCandidate);
		theModel.addAttribute("numberRecruitment", numberRecruitment);
		theModel.addAttribute("numberCompany", numberCompany);

		return "public/home";
	}
}
