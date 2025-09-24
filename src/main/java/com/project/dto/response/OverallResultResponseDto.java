package com.project.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.project.domain.analysis.Dialogue;
import com.project.domain.analysis.OverallResult;
import com.project.domain.analysis.Risk;
import com.project.dto.ConfidenceScoresDto;

public record OverallResultResponseDto(
		Long id,
		String dollId,
		int dialogueCount,
		int charLength,
		Risk label,
		ConfidenceScoresDto confidenceScores,
		String fullText, 
		List<String> evidence,
		String summary
) {

    public OverallResultResponseDto(OverallResult entity) {
        this(
        	entity.getId(),
            entity.getDoll().getId(),
            entity.getDialogues().size(),
            entity.getDialogues().stream().map(Dialogue::getText).collect(Collectors.joining(" ")).length(),
            entity.getLabel(),
            new ConfidenceScoresDto(
                entity.getConfidenceScores().getPositive(),
                entity.getConfidenceScores().getDanger(),
                entity.getConfidenceScores().getCritical(),
                entity.getConfidenceScores().getEmergency()
            ),
            entity.getDialogues().stream().map(Dialogue::getText).collect(Collectors.joining(" ")),
            entity.getReason().getReasons(),
            entity.getReason().getSummary()
        );
    }
}