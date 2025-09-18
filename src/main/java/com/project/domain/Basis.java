package com.project.domain;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Basis {
    @ElementCollection
    @CollectionTable(name = "evidence", joinColumns = @JoinColumn(name = "analysis_result_id"))
    @Column(name = "evidence_text")
    private List<String> evidence;
    @Column(columnDefinition = "TEXT")
    private String summary;
}