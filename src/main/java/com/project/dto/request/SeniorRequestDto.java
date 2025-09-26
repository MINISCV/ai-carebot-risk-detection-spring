package com.project.dto.request;

import java.time.LocalDate;

import com.project.domain.senior.Residence;
import com.project.domain.senior.Sex;

public record SeniorRequestDto(
		String dollId,
		String name, 
		LocalDate birthDate,
		Sex sex, 
		Residence residence,
		String phone,
		String address,
		String gu,
		String dong,
		String note, 
		String guardianName,
		String guardianPhone,
		String relationship,
		String guardianNote,
		String diseases, 
		String medications,
		String diseaseNote
) {
}