package com.project.dto.response;

import java.time.LocalDate;

import com.project.domain.senior.Senior;
import com.project.domain.senior.Sex;

public record SeniorResponseDto(
        Long id,
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
    public SeniorResponseDto(Senior senior) {
        this(
            senior.getId(),
            senior.getDoll().getId(),
            senior.getName(),
            senior.getBirthDate(),
            senior.getSex(),
            senior.getPhone(),
            senior.getAddress(),
            senior.getNote(),
            senior.getGuardian().getGuardianName(),
            senior.getGuardian().getGuardianPhone(),
            senior.getGuardian().getRelationship(),
            senior.getGuardian().getGuardianNote(),
            senior.getMedicalInfo().getDiseases(),
            senior.getMedicalInfo().getMedications()
        );
    }
}