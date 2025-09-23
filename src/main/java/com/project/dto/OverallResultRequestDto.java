package com.project.dto;

public record OverallResultRequestDto(String dollId, int dialogueCount, int charLength, String label,
		ConfidenceScoresDto confidenceScores, String fullText, ReasonDto reason) {
}