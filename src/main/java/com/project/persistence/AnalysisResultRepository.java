package com.project.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.domain.AnalysisResult;

public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, Long>{

}
