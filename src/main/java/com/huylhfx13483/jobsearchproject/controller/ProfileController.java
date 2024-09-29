package com.huylhfx13483.jobsearchproject.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.huylhfx13483.jobsearchproject.dto.CompanyDto;
import com.huylhfx13483.jobsearchproject.dto.UserDto;
import com.huylhfx13483.jobsearchproject.entity.Company;
import com.huylhfx13483.jobsearchproject.entity.Cv;
import com.huylhfx13483.jobsearchproject.entity.User;
import com.huylhfx13483.jobsearchproject.service.CompanyService;
import com.huylhfx13483.jobsearchproject.service.FileService;
import com.huylhfx13483.jobsearchproject.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private FileService fileService;

	@GetMapping("/showProfile")
	public String showProfile(Model theModel, Principal principal) {

		// Lay ra User da dang nhap thong qua email
		User userInformation = getAuthenticatedUser(principal);
		// Lay ra cong ty cua nguoi dung
		Company companyInformation = null;

		if (userInformation.getCompany() != null) {
			companyInformation = userInformation.getCompany();
		}

		// Them user da dang nhap vao model
		theModel.addAttribute("userInformation", userInformation);
		theModel.addAttribute("companyInformation", companyInformation);

		return "public/profile";
	}

	// Cap nhat hinh anh ca nhan hoac cong ty
	@PostMapping("/updateImage")
	public String updateImage(@RequestParam("file") MultipartFile file, @RequestParam("type") String type, Model model,
			Principal principal) throws IOException {

		// Kiem tra loai file co phai la hinh anh hay khong
		if (!file.getContentType().startsWith("image/")) {
			model.addAttribute("imageError", "Chỉ cho phép upload file hình ảnh.");
			return "redirect:/profile/showProfile";
		}

		// Luu file vao he thong
		// Tao duong dan luu trong database
		String dtPath = fileService.saveFile(file, "images");

		// Lay ra User da dang nhap thong qua email
		User user = getAuthenticatedUser(principal);

		// Kiem tra xem file muốn luu là image user hay logo company
		if (type.equals("user")) {
			// set Image voi duong dan file
			user.setImage(dtPath);

			// Luu user voi duong dan file
			userService.save(user);
		} else {
			Company company = user.getCompany();

			// Set logo voi duong dan file
			company.setLogo(dtPath);

			// Luu company
			user.setCompany(company);
			userService.save(user);
		}

		return "redirect:/profile/showProfile";
	}

	// cap nhat file pdf
	@PostMapping("/updateCvPdf")
	public String updateCvPdf(@RequestParam("file") MultipartFile file, Model model, Principal principal)
			throws IOException {

		// Kiem tra loai file co phai la pdf hay khong
		if (!file.getContentType().equals("application/pdf")) {
			model.addAttribute("pdfError", "Chỉ cho phép upload file pdf.");
			return "redirect:/profile/showProfile";
		}

		// Luu file vao he thong
		// Tao duong dan luu trong database
		String dtPath = fileService.saveFile(file, "cv");

		// Lay ra User da dang nhap thong qua email
		User user = getAuthenticatedUser(principal);

		// Khoi tao cv
		Cv cv = new Cv();

		// Luu duong dan vao cv
		cv.setFileName(dtPath);

		// Them cv vao user va luu user vao database
		user.setCv(cv);
		userService.save(user);

		return "redirect:/profile/showProfile";
	}

	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute("userInformation") UserDto userDto, Principal principal,
			HttpServletRequest request, HttpServletResponse response) {

		// Lay ra User da dang nhap thong qua email
		User user = getAuthenticatedUser(principal);

		// Set cac thong so update
		user.setEmail(userDto.getEmail());
		user.setFullName(userDto.getFullName());
		user.setAddress(userDto.getAddress());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setDescription(userDto.getDescription());

		// save user
		userService.save(user);

		// Thuc hien logout thu cong
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

		// Chuyen huong den trang dang nhap sau khi dang xuat
		return "redirect:/auth/loginRegisterPage";
	}

	@PostMapping("/updateCompany")
	public String updateCompany(@ModelAttribute("companyInformation") CompanyDto companyDto, Principal principal,
			HttpServletRequest request, HttpServletResponse response) {

		// Lay ra user da dang nhap thong qua email
		User user = getAuthenticatedUser(principal);

		// Lay ra company cua user
		Company company = user.getCompany();

		// Set cac thong so update
		company.setEmail(companyDto.getEmail());
		company.setCompanyName(companyDto.getCompanyName());
		company.setAddress(companyDto.getAddress());
		company.setPhoneNumber(companyDto.getPhoneNumber());
		company.setDescription(companyDto.getDescription());

		// save company
		companyService.save(company);

		return "redirect:/profile/showProfile";
	}

	// Xem file cv
	@GetMapping("/cv")
	public ResponseEntity<Resource> reviewFile(Principal principal) {
		try {
			// Lay user tu thong tin dang nhap
			User user = getAuthenticatedUser(principal);

			if (user == null || user.getCv() == null) {
				return ResponseEntity.notFound().build();
			}

			String fileName = user.getCv().getFileName(); // Lay ten file

			if (fileName == null) {
				return ResponseEntity.notFound().build();
			}

			Path fileLocation = Paths
					.get(System.getProperty("user.dir"), "src", "main", "resources", "static", fileName).normalize();

			// Tao mot doi tuong Resource dai dien cho file
			Resource resource = new UrlResource(fileLocation.toUri());

			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);

		} catch (MalformedURLException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	public User getAuthenticatedUser(Principal principal) {
		// Lay email tu nguoi dung dang nhap
		String email = principal.getName();

		// Lay ra user da dang nhap thong qua email
		return userService.findByEmail(email);
	}

}
