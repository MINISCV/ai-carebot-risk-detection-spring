package com.project.domain.senior;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
	private String address;
	private String gu;
	private String dong;
    
    @Builder
    public Address(String address, String gu, String dong) {
        this.address = address;
        this.gu = gu;
        this.dong = dong;
    }
}