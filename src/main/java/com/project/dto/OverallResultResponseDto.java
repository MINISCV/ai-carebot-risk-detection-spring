package com.project.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.project.domain.ConfidenceScores;
import com.project.domain.OverallResult;
import com.project.domain.Reason;
import com.project.domain.Risk;

public record OverallResultResponseDto(
        Long id,
        LocalDateTime timestamp,
        Risk label,
        ConfidenceScores confidenceScores,
        Reason reason,
        List<DialogueResponseDto> dialogues
) {
    public OverallResultResponseDto(OverallResult overallResult) {
        this(
            overallResult.getId(),
            overallResult.getTimestamp(),
            overallResult.getLabel(),
            overallResult.getConfidenceScores(),
            overallResult.getReason(),
            overallResult.getDialogues().stream()
                    .map(DialogueResponseDto::new)
                    .collect(Collectors.toList())
        );
    }
}