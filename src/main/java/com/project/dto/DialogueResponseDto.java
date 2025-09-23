package com.project.dto;

import java.time.LocalDateTime;

import com.project.domain.ConfidenceScores;
import com.project.domain.Dialogue;
import com.project.domain.Risk;

public record DialogueResponseDto(
        Long id,
        String text,
        LocalDateTime utteredAt,
        Risk label,
        ConfidenceScores confidenceScores
) {
    public DialogueResponseDto(Dialogue dialogue) {
        this(
            dialogue.getId(),
            dialogue.getText(),
            dialogue.getUtteredAt(),
            dialogue.getLabel(),
            dialogue.getConfidenceScores()
        );
    }
}