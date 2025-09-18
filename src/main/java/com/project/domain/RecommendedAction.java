package com.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class RecommendedAction {
    @Column(name = "action_code")
    private String code;
    @Column(name = "action_description", columnDefinition = "TEXT")
    private String description;
}