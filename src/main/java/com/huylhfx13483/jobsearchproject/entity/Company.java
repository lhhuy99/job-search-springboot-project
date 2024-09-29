package com.huylhfx13483.jobsearchproject.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "address")
	private String address;

	@Column(name = "description")
	private String description;

	@Column(name = "email")
	private String email;

	@Column(name = "logo")
	private String logo;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "phone_number")
	private String phoneNumber;
	
	@OneToMany(mappedBy = "company", cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH })
	private List<Recruitment> recruitments;
	
	@OneToMany(mappedBy = "company", cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH })
	private List<User> users;

	public Company() {

	}

	public Company(String address, String description, String email, String logo, String companyName,
			String phoneNumber) {
		this.address = address;
		this.description = description;
		this.email = email;
		this.logo = logo;
		this.companyName = companyName;
		this.phoneNumber = phoneNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public List<Recruitment> getRecruitments() {
		return recruitments;
	}

	public void setRecruitments(List<Recruitment> recruitments) {
		this.recruitments = recruitments;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	// convenience method
	public void add(Recruitment tempRecruitment) {
		if (recruitments == null) {
			recruitments = new ArrayList<>();
		}
		
		recruitments.add(tempRecruitment);
		tempRecruitment.setCompany(this);
	}
	
	// convenience method
	public void add(User tempUser) {
		if (users == null) {
			users = new ArrayList<>();
		}
		
		users.add(tempUser);
		tempUser.setCompany(this);
	}
}
