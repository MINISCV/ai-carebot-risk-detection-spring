package com.project.domain.senior;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicalInfo {
    private String diseases;
    private String medications;
    
    @Builder
    public MedicalInfo(String diseases, String medications) {
        this.diseases = diseases;
        this.medications = medications;
    }
}