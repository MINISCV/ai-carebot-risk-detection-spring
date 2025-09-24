package com.project.dto.response;

import com.project.domain.analysis.Risk;
import com.project.dto.ConfidenceScoresDto;
import com.project.dto.ReasonDto;

public record OverallResultPythonResponseDto(
		String dollId,
		int dialogueCount,
		int charLength,
		Risk label,
		ConfidenceScoresDto confidenceScores,
		String fullText, 
		ReasonDto reason
) {
}