package com.project.dto.response;

import java.util.List;

public record AnalysisResponseWithIdDto(
		Long id,
		OverallResultPythonResponseDto overallResult,
		List<DialogueAnalysisResponseDto> dialogueResult
) {
}