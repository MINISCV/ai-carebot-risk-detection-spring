package com.project.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.project.domain.analysis.Risk;
import com.project.domain.senior.Sex;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OverallResultSearchCondition {
	@Positive(message = "시니어 ID는 양수여야 합니다.")
    private Long seniorId;
	
    private String name;
    private Sex sex;
    private String gu;
    private String dong;
    
    @Min(value = 1, message = "나이 그룹은 1 이상의 값이어야 합니다.")
    private Integer ageGroup;

    private String dollId;
    
    private Risk label;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "검색 시작일은 현재 또는 과거 날짜여야 합니다.")
    private LocalDate startDate;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "검색 종료일은 현재 또는 과거 날짜여야 합니다.")
    private LocalDate endDate;
}