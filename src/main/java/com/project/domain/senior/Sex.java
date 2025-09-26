package com.project.domain.senior;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sex {
	MALE,
	FEMALE;

    @JsonCreator
    public static Sex from(String value) {
        String upperValue = value.toUpperCase();
        for (Sex sex : Sex.values()) {
            if (sex.name().equals(upperValue)) {
                return sex;
            }
        }
        throw new IllegalArgumentException("알 수 없는 Sex 값입니다: " + value);
    }
}
