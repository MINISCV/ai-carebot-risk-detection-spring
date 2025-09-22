package com.project.dto;

import java.time.LocalDate;

import com.project.domain.Sex;

public record SeniorDto(String dollId, String name, LocalDate birthDate, Sex sex, String phone, String address,
		String note, String guardianName, String guardianPhone, String relationship, String guardianNote,
		String diseases, String medications) {
}