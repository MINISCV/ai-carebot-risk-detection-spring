package com.project.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.project.domain.analysis.Risk;
import com.project.domain.senior.Residence;
import com.project.domain.senior.Senior;
import com.project.domain.senior.Sex;

public record SeniorDetailResponseDto(
        Long id,
		String dollId,
		String name, 
		String photoUrl,
		LocalDate birthDate,
		Sex sex,
		Risk state,
		Residence residence,
		String phone,
		String address,
		String addressDetail,
		String gu,
		String dong,
        Double latitude,
        Double longitude,
		String note, 
		String guardianName,
		String guardianPhone,
		String relationship,
		String guardianNote,
		String diseases, 
		String medications,		
		String diseaseNote,
        List<RecentOverallResultDto> recentOverallResults
) {
    public SeniorDetailResponseDto(Senior senior, List<RecentOverallResultDto> recentResults) {
        this(
            senior.getId(),
            senior.getDoll() != null ? senior.getDoll().getId() : null,
            senior.getName(),
            senior.getPhoto() != null ? "seniors/photos/" + senior.getPhoto() : null,
            senior.getBirthDate(),
            senior.getSex(),
            senior.getState(),
            senior.getResidence(),
            senior.getPhone(),
            senior.getAddress().getAddress(),
            senior.getAddress().getDetail(),
            senior.getAddress().getGu().getKoreanName(),
            senior.getAddress().getDong().getKoreanName(),
            senior.getAddress().getLatitude(),
            senior.getAddress().getLongitude(),
            senior.getNote(),
            senior.getGuardian().getGuardianName(),
            senior.getGuardian().getGuardianPhone(),
            senior.getGuardian().getRelationship(),
            senior.getGuardian().getGuardianNote(),
            senior.getMedicalInfo().getDiseases(),
            senior.getMedicalInfo().getMedications(),            
            senior.getMedicalInfo().getDiseaseNote(),
            recentResults
        );
    }
}