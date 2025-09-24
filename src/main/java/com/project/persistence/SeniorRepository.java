package com.project.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.domain.senior.Senior;

public interface SeniorRepository extends JpaRepository<Senior, Long>{
	@Query("SELECT s FROM Senior s LEFT JOIN FETCH s.doll WHERE s.id = :id")
	Optional<Senior> findByIdWithDoll(Long id);

	@Query("SELECT s FROM Senior s LEFT JOIN FETCH s.doll")
	List<Senior> findAllWithDoll();
}
