package com.project.dto;

import java.util.List;

public record AnalysisRequestDto(OverallResultRequestDto overallResult,
		List<DialogueDto> dialogueResult) {
}