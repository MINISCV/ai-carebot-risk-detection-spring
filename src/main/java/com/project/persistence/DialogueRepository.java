package com.project.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.Dialogue;

public interface DialogueRepository extends JpaRepository<Dialogue, Long>{
    @Transactional
    @Modifying
    @Query(
    	    value = "INSERT INTO dialogue (doll_id, text, uttered_at, analysis_result_id, " +
    	            "individual_risk_level, individual_positive_score, individual_danger_score, " +
    	            "individual_critical_score, individual_emergency_score) " +
    	            "VALUES (:#{#entity.dollId}, :#{#entity.text}, :#{#entity.utteredAt}, :#{#entity.analysisResult.id}, " +
    	            ":#{#entity.individualRiskLevel.name()}, :#{#entity.individualPositiveScore}, :#{#entity.individualDangerScore}, " +
    	            ":#{#entity.individualCriticalScore}, :#{#entity.individualEmergencyScore}) " +
    	            "ON DUPLICATE KEY UPDATE " +
    	            "text = VALUES(text), " +
    	            "analysis_result_id = VALUES(analysis_result_id), " +
    	            "individual_risk_level = VALUES(individual_risk_level), " +
    	            "individual_positive_score = VALUES(individual_positive_score), " +
    	            "individual_danger_score = VALUES(individual_danger_score), " +
    	            "individual_critical_score = VALUES(individual_critical_score), " +
    	            "individual_emergency_score = VALUES(individual_emergency_score)",
    	    nativeQuery = true
    	)
    void upsert(@Param("entity") Dialogue entity);
    
    List<Dialogue> findByDollIdOrderByUtteredAtAsc(String dollId);
}
