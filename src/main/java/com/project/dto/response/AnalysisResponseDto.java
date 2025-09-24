package com.project.dto.response;

import java.util.List;

public record AnalysisResponseDto(
		OverallResultResponseDto overallResult,
		List<DialogueAnalysisResponseDto> dialogueResult
) {
}