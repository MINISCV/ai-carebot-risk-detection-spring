package com.project.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private Risk riskLevel;
    @Column(name = "positive_score")
    private Double positiveScore;
    @Column(name = "danger_score")
    private Double dangerScore;
    @Column(name = "critical_score")
    private Double criticalScore;
    @Column(name = "emergency_score")
    private Double emergencyScore;
    @Embedded
    private Basis basis;
    @Embedded
    private RecommendedAction recommendedAction;
    @OneToMany(mappedBy = "analysisResult", orphanRemoval = true)
    @Builder.Default
    private List<Dialogue> dialogues = new ArrayList<>();
}