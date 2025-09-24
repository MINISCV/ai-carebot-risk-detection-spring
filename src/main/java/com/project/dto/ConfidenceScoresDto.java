package com.project.dto;

public record ConfidenceScoresDto(
		Double positive,
		Double danger,
		Double critical,
		Double emergency
) {
}