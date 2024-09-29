package com.huylhfx13483.jobsearchproject.dto;

public class CompanyDto {
	private Integer companyId;
    private String companyName;
    private String logo;
    private Long recruitmentCount;
    private String address;
    private String description;
    private String email;
    private String phoneNumber;

    public CompanyDto() {
    	
    }
    
    public CompanyDto(Integer companyId, String companyName, String logo, Long recruitmentCount) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.logo = logo;
		this.recruitmentCount = recruitmentCount;
	}
    
    public CompanyDto(String companyName, String logo, Long recruitmentCount, String address, String description,
			String email, String phoneNumber) {
		this.companyName = companyName;
		this.logo = logo;
		this.recruitmentCount = recruitmentCount;
		this.address = address;
		this.description = description;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	// Getters and setters
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getRecruitmentCount() {
        return recruitmentCount;
    }

    public void setRecruitmentCount(Long recruitmentCount) {
        this.recruitmentCount = recruitmentCount;
    }

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
    
}
