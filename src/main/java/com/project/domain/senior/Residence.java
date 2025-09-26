package com.project.domain.senior;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Residence {
    SINGLE_FAMILY_HOME,
    MULTIPLEX_HOUSING,
    MULTI_FAMILY_HOUSING,
    APARTMENT;
	
    @JsonCreator
    public static Residence from(String value) {
        String upperValue = value.toUpperCase();
        for (Residence residence : Residence.values()) {
            if (residence.name().equals(upperValue)) {
                return residence;
            }
        }
        throw new IllegalArgumentException("알 수 없는 Residence 값입니다: " + value);
    }
}