package com.project.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.domain.Senior;

public interface SeniorRepository extends JpaRepository<Senior, Long>{

}
