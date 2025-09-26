package com.project.dto.response;

import com.project.domain.analysis.Dialogue;
import com.project.domain.analysis.Risk;
import com.project.dto.ConfidenceScoresDto;
import java.time.LocalDateTime;

public record DialogueDetailDto(
        Long id,
        String text,
        LocalDateTime utteredAt,
        Risk label,
        ConfidenceScoresDto confidenceScores
) {
    public static DialogueDetailDto from(Dialogue dialogue) {
        return new DialogueDetailDto(
                dialogue.getId(),
                dialogue.getText(),
                dialogue.getUtteredAt(),
                dialogue.getLabel(),
                new ConfidenceScoresDto(
                        dialogue.getConfidenceScores().getPositive(),
                        dialogue.getConfidenceScores().getDanger(),
                        dialogue.getConfidenceScores().getCritical(),
                        dialogue.getConfidenceScores().getEmergency()
                )
        );
    }
}