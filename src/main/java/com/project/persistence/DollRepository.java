package com.project.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.domain.senior.Doll;

public interface DollRepository extends JpaRepository<Doll, String>{
	@Query("SELECT d FROM Doll d LEFT JOIN FETCH d.senior WHERE d.id = :id")
    Optional<Doll> findByIdWithSenior(String id);

    @Query("SELECT d FROM Doll d LEFT JOIN FETCH d.senior")
    List<Doll> findAllWithSenior();
}
