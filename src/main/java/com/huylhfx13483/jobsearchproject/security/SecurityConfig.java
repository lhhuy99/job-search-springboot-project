package com.huylhfx13483.jobsearchproject.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public UserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // Cau lenh SQL de lay thong tin nguoi dung
        manager.setUsersByUsernameQuery(
            "SELECT email, password, status FROM user WHERE email = ?"
        );

        // Cau lenh SQL de lay quyen cua nguoi dung tu bang role
        manager.setAuthoritiesByUsernameQuery(
            "SELECT u.email, r.role_name " +
            "FROM user u " +
            "JOIN role r ON u.role_id = r.id " +
            "WHERE u.email = ?"
        );

        return manager;
    }
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(authz -> 
	        	authz
	        		.requestMatchers("/homePage/home","/assets/**","/auth/register").permitAll() // Cho phep truy cap khong xac thuc vao cac file css, js
	        		.requestMatchers("/post/showPostRecruitment","/post/showPostList").hasRole("EMPLOYER")
	        		.anyRequest().authenticated()  // Yeu cau xac thuc cho tat ca cac yeu cau khac
	        )
	        .formLogin(formLogin ->
	            formLogin
	                .loginPage("/auth/loginRegisterPage")  // Cau hinh trang dang nhap tuy chinh
                    .loginProcessingUrl("/perform_login")  // Dat URL de xu ly yeu cau dang nhap
	                .defaultSuccessUrl("/homePage/home", true)  // Dat URL thanh cong mac dinh sau khi dang nhap thanh cong
	                .permitAll()  // Cho phep tat ca nguoi dung truy cap trang dang nhap
	        )
	        .logout(logout ->
	            logout
	            	.logoutUrl("/logout") // URL cho logout
	            	.logoutSuccessUrl("/auth/loginRegisterPage")
	            	.permitAll()
	        );
	        

	    return http.build();
	}
	
}
