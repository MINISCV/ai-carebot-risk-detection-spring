package com.project.domain.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Risk {
	POSITIVE, 
	DANGER, 
	CRITICAL, 
	EMERGENCY;
	
    @JsonCreator
    public static Risk from(String value) {
        String upperValue = value.toUpperCase(); 
        for (Risk risk : Risk.values()) {
            if (risk.name().equals(upperValue)) {
                return risk;
            }
        }
        throw new IllegalArgumentException("알 수 없는 Risk 값입니다: " + value);
    }
}
