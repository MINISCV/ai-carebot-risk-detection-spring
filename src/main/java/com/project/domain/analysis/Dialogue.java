package com.project.domain.analysis;

import java.time.LocalDateTime;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dialogue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	private String text;
	
	private LocalDateTime utteredAt;
	
    @Enumerated(EnumType.STRING)
    private Risk label;
    
    @Embedded
    private ConfidenceScores confidenceScores;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "overall_result_id")
    private OverallResult overallResult;
	
    @Builder
    public Dialogue(String text, LocalDateTime utteredAt, Risk label, ConfidenceScores confidenceScores) {
        this.text = text;
        this.utteredAt = utteredAt;
        this.label = label;
        this.confidenceScores = confidenceScores;
    }

    protected void setOverallResult(OverallResult overallResult) {
        this.overallResult = overallResult;
    }
}
