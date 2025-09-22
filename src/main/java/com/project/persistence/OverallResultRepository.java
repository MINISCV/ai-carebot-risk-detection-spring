package com.project.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.domain.OverallResult;

public interface OverallResultRepository extends JpaRepository<OverallResult, Long>{

}
