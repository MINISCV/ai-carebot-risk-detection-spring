package com.project.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.domain.Doll;

public interface DollRepository extends JpaRepository<Doll, String>{

}
