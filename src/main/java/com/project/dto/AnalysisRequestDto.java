package com.project.dto;

import java.util.List;

public record AnalysisRequestDto(String result, String validationMsg, OverallResultDto overallResult,
		List<DialogueDto> dialogueResult) {
}