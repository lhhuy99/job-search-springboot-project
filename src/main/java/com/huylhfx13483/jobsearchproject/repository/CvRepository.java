package com.huylhfx13483.jobsearchproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huylhfx13483.jobsearchproject.entity.Cv;

@Repository
public interface CvRepository extends JpaRepository<Cv, Integer> {

}
