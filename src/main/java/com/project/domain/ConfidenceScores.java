package com.project.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfidenceScores {
    private Double positive;
    private Double danger;
    private Double critical;
    private Double emergency;
    
    @Builder
    public ConfidenceScores(Double positive, Double danger, Double critical, Double emergency) {
        this.positive = positive;
        this.danger = danger;
        this.critical = critical;
        this.emergency = emergency;
    }
}