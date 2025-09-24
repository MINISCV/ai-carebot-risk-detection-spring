package com.project.dto.response;

import java.util.List;

public record AnalysisResponseDto(
		OverallResultPythonResponseDto overallResult,
		List<DialogueAnalysisResponseDto> dialogueResult
) {
}