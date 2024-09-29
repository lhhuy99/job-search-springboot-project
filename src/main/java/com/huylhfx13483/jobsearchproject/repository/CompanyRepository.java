package com.huylhfx13483.jobsearchproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.huylhfx13483.jobsearchproject.entity.Company;
import com.huylhfx13483.jobsearchproject.dto.CompanyDto;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	// Tim cac cong ty co so luong vi tri tuyen dung nhieu nhat (se ket hop voi dto va service de tim ra)
	// va so luong tuyen dung do
	// su dung dto CompanyRecruitmentCount hung du lieu
	@Query("SELECT new com.huylhfx13483.jobsearchproject.dto.CompanyDto(c.id, c.companyName, c.logo, COUNT(r.id)) " +
			"FROM Company c JOIN c.recruitments r " +
			"GROUP BY c.id " +
			"ORDER BY COUNT(r.id) DESC")
	List<CompanyDto> findCompaniesWithMostRecruitments();
}
