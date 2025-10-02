package com.project.domain.senior;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
	private String address;
	
	@Enumerated(EnumType.STRING)
	private Gu gu;

	@Enumerated(EnumType.STRING)
	private Beopjeongdong dong;
    
    @Builder
    public Address(String address, Gu gu, Beopjeongdong dong) {
        this.address = address;
        this.gu = gu;
        this.dong = dong;
    }
}