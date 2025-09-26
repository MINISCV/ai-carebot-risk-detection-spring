package com.project.dto.request;

import com.project.domain.analysis.Risk;
import com.project.domain.senior.Sex;
import lombok.Data;

@Data
public class SeniorSearchCondition {
    private Long seniorId;
    private String name;
    private String phone;
    private Sex sex;
    private String gu;
    private String dong;
    private Risk state;
    private String dollId;
    private Integer ageGroup;
}