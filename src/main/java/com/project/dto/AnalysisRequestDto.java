package com.project.dto;

import java.util.List;

public record AnalysisRequestDto(OverallResultDto overallResult, List<DialogueDto> dialogueResult) {
}