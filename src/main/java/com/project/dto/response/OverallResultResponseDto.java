package com.project.dto.response;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.project.domain.analysis.Dialogue;
import com.project.domain.analysis.OverallResult;
import com.project.domain.analysis.Risk;
import com.project.dto.ConfidenceScoresDto;
import com.project.dto.EvidenceDto;
import com.project.dto.ReasonDto;

public record OverallResultResponseDto(
		String dollId,
		int dialogueCount,
		int charLength,
		Risk label,
		ConfidenceScoresDto confidenceScores,
		String fullText, 
		ReasonDto reason
) {

    public OverallResultResponseDto(OverallResult entity) {
        this(
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
            new ReasonDto(
                reasonsToEvidenceDtos(entity.getReason().getReasons()),
                entity.getReason().getSummary()
            )
        );
    }

    private static List<EvidenceDto> reasonsToEvidenceDtos(List<String> reasonTexts) {
        if (reasonTexts == null) {
            return Collections.emptyList();
        }
        return reasonTexts.stream()
                .map(text -> new EvidenceDto(0, text, null))
                .collect(Collectors.toList());
    }
}