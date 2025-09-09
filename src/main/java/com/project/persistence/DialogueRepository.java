package com.project.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.domain.Dialogue;

public interface DialogueRepository extends JpaRepository<Dialogue, Long>{

}
