package com.project.dto;

public record OverallResultDto(String dollId, int dialogueCount, int charLength, String label,
		ConfidenceScoresDto confidenceScores, ReasonDto reason) {
}