package com.project.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reason {
    @ElementCollection
    @CollectionTable(name = "reason", joinColumns = @JoinColumn(name = "overall_result_id"))
    @Column(name = "reason_text")
    private List<String> reasons = new ArrayList<>();
    
    @Lob
    private String summary;
    
    @Builder
    public Reason(List<String> reasons, String summary) {
        if (reasons != null) {
            this.reasons = reasons;
        }
        this.summary = summary;
    }
}