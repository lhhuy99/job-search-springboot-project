package com.huylhfx13483.jobsearchproject.dto;

public class RecruitmentDto {

	private Integer id;
	private String address;
	private String createdAt;
	private String description;
	private String experience;
	private Integer quantity;
	private String salary;
	private String title;
	private String type;
	private String deadline;

	public RecruitmentDto() {

	}

	public RecruitmentDto(String address, String createdAt, String description, String experience, Integer quantity,
			String salary, String title, String type, String deadline) {
		super();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
