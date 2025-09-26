package com.project.dto.request;

import com.project.domain.analysis.Risk;
import com.project.domain.senior.Sex;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class OverallResultSearchCondition {
    private Long seniorId;
    private String name;
    private Sex sex;
    private String gu;
    private String dong;
    private Integer ageGroup;

    private String dollId;
    
    private Risk label;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
}