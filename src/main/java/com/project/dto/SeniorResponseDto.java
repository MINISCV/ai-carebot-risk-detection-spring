package com.project.dto;

import java.time.LocalDate;

import com.project.domain.Senior;
import com.project.domain.Sex;

public record SeniorResponseDto(
        Long id,
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
            senior.getName(),
            senior.getBirthDate(),
            senior.getSex(),
            senior.getPhone(),
            senior.getAddress(),
            senior.getNote(),
            senior.getGuardianName(),
            senior.getGuardianPhone(),
            senior.getRelationship(),
            senior.getGuardianNote(),
            senior.getDiseases(),
            senior.getMedications()
        );
    }
}