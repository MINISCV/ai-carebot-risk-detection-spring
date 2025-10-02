package com.project.dto.request;

import com.project.domain.analysis.Risk;
import com.project.domain.senior.Gu;
import com.project.domain.senior.Beopjeongdong;
import com.project.domain.senior.Sex;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SeniorSearchCondition {
    @Positive(message = "시니어 ID는 양수여야 합니다.")
    private Long seniorId;
    
    private String name;
    private String phone;
    private Sex sex;
    private Gu gu;
    private Beopjeongdong dong;
    private Risk state;
    private String dollId;
    
    @Min(value = 1, message = "나이 그룹은 1 이상의 값이어야 합니다.")
    private Integer ageGroup;
}