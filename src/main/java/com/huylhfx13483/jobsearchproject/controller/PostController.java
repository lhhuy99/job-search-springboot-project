package com.huylhfx13483.jobsearchproject.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huylhfx13483.jobsearchproject.dto.RecruitmentDto;
import com.huylhfx13483.jobsearchproject.entity.Category;
import com.huylhfx13483.jobsearchproject.entity.Company;
import com.huylhfx13483.jobsearchproject.entity.Recruitment;
import com.huylhfx13483.jobsearchproject.entity.User;
import com.huylhfx13483.jobsearchproject.service.CategoryService;
import com.huylhfx13483.jobsearchproject.service.CompanyService;
import com.huylhfx13483.jobsearchproject.service.RecruitmentService;
import com.huylhfx13483.jobsearchproject.service.UserService;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private RecruitmentService recruitmentService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	CategoryService categoryService;

	@GetMapping("/showPostList")
	public String showPostList(@RequestParam("page") int page, @RequestParam("companyId") Integer companyId,
			Model model, Principal principal) {

		// Lay ra User da dang nhap thong qua email
		User userLoggedIn = getAuthenticatedUser(principal);
		// Them user da dang nhap vao model
		model.addAttribute("userLoggedIn", userLoggedIn);

		// Lay doi tuong Company tu Id
		Company company = companyService.findById(companyId);

		// Lay danh sach bai dang voi phan trang, gioi han 5 bai moi trang
		Page<Recruitment> recruitmentsPage = recruitmentService.findAllByCompany(company, page, 5);

		model.addAttribute("list", recruitmentsPage);
		model.addAttribute("numberPage", page);
		model.addAttribute("recruitmentList", recruitmentsPage.getContent());

		return "public/post-list";
	}

	@ExceptionHandler(RuntimeException.class)
	public String handleRuntimeException(RuntimeException ex, Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		return "errors/exception";
	}

	// Hien thi form them bai tuyen dung
	@GetMapping("/showPostRecruitment")
	public String showPostRecruitment(Principal principal, Model model) {

		// Lay ra User da dang nhap thong qua email
		User userLoggedIn = getAuthenticatedUser(principal);

		// Lay ra list cac danh muc (category)
		List<Category> categories = categoryService.findAll();

		model.addAttribute("userLoggedIn", userLoggedIn);
		model.addAttribute("recruitment", new Recruitment());
		model.addAttribute("categories", categories);

		return "public/post-job";
	}

	// Them moi tin tuyen dung
	@PostMapping("/postRecruitment")
	public String postRecruitment(@ModelAttribute("recruitment") RecruitmentDto recruitmentDto,
			@RequestParam("category_id") Integer categoryId, Principal principal, Model model,
			RedirectAttributes redirectAttributes) {

		// Tao moi Recruitment
		Recruitment recruitment = new Recruitment();

		// Thiet lap cac thong tin tuyen dung
		recruitment = editPost(recruitmentDto, categoryId, principal, model, recruitment);

		// Luu Recruitment vao database
		recruitmentService.save(recruitment);

		// thong bao success
		// model.addAttribute("success", "Đăng tuyển thành công");
		redirectAttributes.addFlashAttribute("success", "Đăng tuyển thành công");

		return "redirect:/post/showPostRecruitment";

	}

	// Hien thi chi tiet tin tuyen dung
	@GetMapping("/showDetailPost/{recruitmentId}")
	public String showDetailPost(@PathVariable("recruitmentId") Integer recruitmentId, Model model,
			Principal principal) {
		loadRecruitmentDetails(recruitmentId, model, principal);
		return "public/detail-post";
	}

	// Hien thi phan cap nhat thong tin tuyen dung
	@GetMapping("/showUpdatePost/{recruitmentId}")
	public String showUpdatePost(@PathVariable("recruitmentId") Integer recruitmentId, Model model,
			Principal principal) {
		loadRecruitmentDetails(recruitmentId, model, principal);

		// Lay list danh muc, them vao model
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);

		return "public/edit-job";
	}

	// Cap nhat thong tin tuyen dung
	@PostMapping("/updatePost/{recruitmentId}")
	public String updatePost(@ModelAttribute("recruitment") RecruitmentDto recruitmentDto,
			@RequestParam("category_id") Integer categoryId, Principal principal, Model model) {

		// Lay User da dang nhap tu Principal
		User userLoggedIn = getAuthenticatedUser(principal);

		// Lay Recruitment tu database
		Recruitment recruitment = recruitmentService.findById(recruitmentDto.getId());

		// Thiet lap cac thong tin tuyen dung
		recruitment = editPost(recruitmentDto, categoryId, principal, model, recruitment);

		recruitmentService.save(recruitment);

//		return "redirect:/post/showUpdatePost/" + recruitmentDto.getId();
		return "redirect:/post/showPostList?page=0&companyId=" + userLoggedIn.getCompany().getId();
	}

	@PostMapping("/deletePost")
	public String deletePost(@RequestParam("id") Integer id, @RequestParam("compId") Integer compId) {

		recruitmentService.delete(id);

		return "redirect:/post/showPostList?page=0&companyId=" + compId;
	}

	// Tim kiem theo tieu de
	@GetMapping("/searchByTitle")
	public String searchByTitle(@RequestParam("keySearch") String keySearch, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, Model model, Principal principal) {

		// Hien thi so ung cu vien, so cong ty va tin tuyen dung tren trinh duyet
		addNumberIntoModel(model);

		// Lay ra User da dang nhap thong qua email
		User userLoggedIn = getAuthenticatedUser(principal);

		Page<Recruitment> recruitmentPage = recruitmentService.searchByTitle(keySearch, page, size);

		addPaginationToModel(recruitmentPage, userLoggedIn, keySearch, page, model);

		return "public/search-title-result";
	}

	// Chuyen huong sang GET de hien thi ket qua
	// Dung khi nhan nut go back trên trinh duyet
	@PostMapping("/searchByTitle")
	public String searchByTitle(@RequestParam("keySearch") String keySearch, RedirectAttributes redirectAttributes) {
		// Luu tru tu khoa tim kiem vao RedirectAttributes
		redirectAttributes.addAttribute("keySearch", keySearch);
		return "redirect:/post/searchByTitle"; // Chuyen huong sang GET
	}

	// Tim theo ten cong ty
	@GetMapping("/searchByCompanyName")
	public String searchByCompanyName(@RequestParam("keySearch") String keySearch,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model,
			Principal principal) {
		// Hien thi so ung cu vien, so cong ty va tin tuyen dung tren trinh duyet
		addNumberIntoModel(model);

		// Lay ra User da dang nhap thong qua email
		User userLoggedIn = getAuthenticatedUser(principal);

		Page<Recruitment> recruitmentPage = recruitmentService.searchByCompany(keySearch, page, size);

		addPaginationToModel(recruitmentPage, userLoggedIn, keySearch, page, model);

		return "public/search-company-result";
	}

	// Chuyen huong sang GET de hien thi ket qua
	// Dung khi nhan nut go back trên trinh duyet
	@PostMapping("/searchByCompanyName")
	public String searchByCompanyName(@RequestParam("keySearch") String keySearch, RedirectAttributes redirectAttributes) {
		// Luu tru tu khoa tim kiem vao RedirectAttributes
		redirectAttributes.addAttribute("keySearch", keySearch);
		return "redirect:/post/searchByCompanyName"; // Chuyen huong sang GET
	}

	// Tim theo dia chi
	@GetMapping("/searchByAddress")
	public String searchByAddress(@RequestParam("keySearch") String keySearch, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, Model model, Principal principal) {

		// Hien thi so ung cu vien, so cong ty va tin tuyen dung tren trinh duyet
		addNumberIntoModel(model);

		// Lay ra User da dang nhap thong qua email
		User userLoggedIn = getAuthenticatedUser(principal);

		Page<Recruitment> recruitmentPage = recruitmentService.searchByAddress(keySearch, page, size);

		addPaginationToModel(recruitmentPage, userLoggedIn, keySearch, page, model);

		return "public/search-address-result";
	}

	// Chuyen huong sang GET de hien thi ket qua
	// Dung khi nhan nut go back trên trinh duyet
	@PostMapping("/searchByAddress")
	public String searchByAddress(@RequestParam("keySearch") String keySearch, RedirectAttributes redirectAttributes) {
		// Luu tru tu khoa tim kiem vao RedirectAttributes
		redirectAttributes.addAttribute("keySearch", keySearch);
		return "redirect:/post/searchByAddress"; // Chuyen huong sang GET
	}
	
	
	public Recruitment editPost(RecruitmentDto recruitmentDto, Integer categoryId, Principal principal, Model model,
			Recruitment recruitment) {

		// Lay ra User da dang nhap thong qua email
		User userLoggedIn = getAuthenticatedUser(principal);
		// Lay ra Company cua User
		Company company = userLoggedIn.getCompany();

		// Neu Recruitment la null(truong hop post moi) thi tao moi
		if (recruitment == null) {
			recruitment = new Recruitment();
		}

		// Dinh dang ngay thang theo MM/DD/YYYY
		// set deadline va createAt(ngay khoi tao)
		String deadLine = recruitmentDto.getDeadline();

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		LocalDate date = LocalDate.parse(deadLine, inputFormatter);
		LocalDate nowDate = LocalDate.now();

		deadLine = date.format(outputFormatter);
		String currentDate = nowDate.format(outputFormatter);

		// Set cac gia tri cho recruitment
		recruitment.setTitle(recruitmentDto.getTitle());
		recruitment.setDescription(recruitmentDto.getDescription());
		recruitment.setExperience(recruitmentDto.getExperience());
		recruitment.setQuantity(recruitmentDto.getQuantity());
		recruitment.setAddress(recruitmentDto.getAddress());
		recruitment.setSalary(recruitmentDto.getSalary());
		recruitment.setType(recruitmentDto.getType());
		recruitment.setCategory(categoryService.findById(categoryId));
		recruitment.setCompany(company);
		recruitment.setCreatedAt(currentDate);
		recruitment.setDeadline(deadLine);

		return recruitment;
	}

	public User getAuthenticatedUser(Principal principal) {

		// Lay email tu nguoi dung dang nhap
		String email = principal.getName();

		// Lay ra user da dang nhap thong qua email
		return userService.findByEmail(email);
	}

	public void loadRecruitmentDetails(Integer recruitmentId, Model model, Principal principal) {

		// Lay ra User da dang nhap thong qua email
		User userLoggedIn = getAuthenticatedUser(principal);

		// Lay ra recruitment thong qua id
		Recruitment recruitment = recruitmentService.findById(recruitmentId);

		// Dua du lieu vao model
		model.addAttribute("recruitment", recruitment);
		model.addAttribute("userLoggedIn", userLoggedIn);
	}

	public void addNumberIntoModel(Model model) {

		// Tim tong so user voi vai tro la nguoi tim viec
		long numberCandidate = userService.countByRole("ROLE_JOBSEEKER");

		// Tim tong so cong ty
		long numberCompany = companyService.countCompanies();

		// Tim tong so vi tri tuyen dung(tong so luong recruitment)
		long numberRecruitment = recruitmentService.countRecruitments();

		model.addAttribute("numberCandidate", numberCandidate);
		model.addAttribute("numberRecruitment", numberRecruitment);
		model.addAttribute("numberCompany", numberCompany);
	}
	
	public void addPaginationToModel(Page<Recruitment> recruitmentPage, User userLoggedIn, String keySearch, int page, Model model) {
		model.addAttribute("recruitments", recruitmentPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", recruitmentPage.getTotalPages());
		model.addAttribute("keySearch", keySearch);
		model.addAttribute("userLoggedIn", userLoggedIn);
	}
}
