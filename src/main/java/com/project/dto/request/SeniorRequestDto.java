package com.project.dto.request;

import java.time.LocalDate;

import com.project.domain.senior.Sex;

public record SeniorRequestDto(
		String dollId,
		String name, 
		LocalDate birthDate,
		Sex sex, 
		String phone,
		String address,
		String note, 
		String guardianName,
		String guardianPhone,
		String relationship,
		String guardianNote,
		String diseases, 
		String medications
) {
}