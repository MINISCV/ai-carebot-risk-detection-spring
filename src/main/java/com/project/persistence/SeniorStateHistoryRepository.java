package com.project.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.domain.senior.SeniorStateHistory;

public interface SeniorStateHistoryRepository extends JpaRepository<SeniorStateHistory, Long> {

}
