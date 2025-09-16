package com.project.persistence;

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
        value = "INSERT INTO dialogue (doll_id, text, uttered_at) VALUES (:#{#entity.dollId}, :#{#entity.text}, :#{#entity.utteredAt}) " +
                "ON DUPLICATE KEY UPDATE text = :#{#entity.text}",
        nativeQuery = true
    )
    void upsert(@Param("entity") Dialogue entity);
}
