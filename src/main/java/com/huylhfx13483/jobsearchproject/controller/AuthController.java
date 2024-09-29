package com.huylhfx13483.jobsearchproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huylhfx13483.jobsearchproject.dto.UserDto;
import com.huylhfx13483.jobsearchproject.entity.Role;
import com.huylhfx13483.jobsearchproject.entity.User;
import com.huylhfx13483.jobsearchproject.service.RoleService;
import com.huylhfx13483.jobsearchproject.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/loginRegisterPage")
	public String showLoginPage(Model theModel) {
		
		UserDto userDto = new UserDto();
		
		theModel.addAttribute("user", userDto);
		return "public/login";
	}
	
	@PostMapping("/register")
	public String registerUser(Model theModel, @ModelAttribute("user") UserDto userDto, RedirectAttributes redirectAttributes) {
		if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
			User tempUser = new User();
			if (userService.findByEmail(userDto.getEmail()) == null) {
				tempUser.setEmail(userDto.getEmail());
				tempUser.setFullName(userDto.getFullName());
				tempUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
				tempUser.setStatus(1);
			
				// Luu role cung voi user
				Role tempRole = roleService.findByRoleName(userDto.getRole());
				tempUser.setRole(tempRole);
				userService.save(tempUser);
				redirectAttributes.addFlashAttribute("msg_register_success", "Đăng ký thành công");
				
				return "redirect:/auth/loginRegisterPage";
			} else {
				theModel.addAttribute("emailAlreadyExist", "Email đã đăng ký");
				
				return "public/login";
			}
			
		} else {
			theModel.addAttribute("confirmPasswordError", "Mật khẩu không khớp");
			
			return "public/login";
		}
		
	}
}	
