package com.project.dto.response;

import java.time.LocalDateTime;

import com.project.domain.analysis.Risk;
import com.project.dto.ConfidenceScoresDto;

public record DialogueAnalysisResponseDto(
		Long seq,
		String dollId,
		String text,
		LocalDateTime utteredAt,
		Risk label,
		ConfidenceScoresDto confidenceScores
) {
}