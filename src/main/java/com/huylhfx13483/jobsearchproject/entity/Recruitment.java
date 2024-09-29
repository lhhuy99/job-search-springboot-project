package com.huylhfx13483.jobsearchproject.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "recruitment")
public class Recruitment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "address")
	private String address;

	@Column(name = "created_at")
	private String createdAt;

	@Column(name = "description")
	private String description;

	@Column(name = "experience")
	private String experience;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "salary")
	private String salary;

	@Column(name = "title")
	private String title;

	@Column(name = "type")
	private String type;

	@Column(name = "deadline")
	private String deadline;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH })
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH })
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "recruitment", cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH })
	private List<ApplyPost> applyposts;

	public Recruitment() {

	}

	public Recruitment(String address, String createdAt, String description, String experience, int quantity,
			String salary, String title, String type, String deadline) {
		this.address = address;
		this.createdAt = createdAt;
		this.description = description;
		this.experience = experience;
		this.quantity = quantity;
		this.salary = salary;
		this.title = title;
		this.type = type;
		this.deadline = deadline;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<ApplyPost> getApplyposts() {
		return applyposts;
	}

	public void setApplyposts(List<ApplyPost> applyposts) {
		this.applyposts = applyposts;
	}
	
	public void add(ApplyPost tempApplyPost) {
		if (applyposts == null) {
			applyposts = new ArrayList<>();
		}
		
		applyposts.add(tempApplyPost);
		tempApplyPost.setRecruitment(this);
	}
}
