package com.project.domain.senior;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gu {
	DONG_GU("동구"), 
	JUNG_GU("중구"),
	SEO_GU("서구"), 
	YUSEONG_GU("유성구"), 
	DAEDEOK_GU("대덕구");

	private final String koreanName;

	@JsonCreator
	public static Gu from(String value) {
		if (value == null)
			return null;
		String upperValue = value.toUpperCase();
		for (Gu gu : Gu.values()) {
			if (gu.name().equals(upperValue) || gu.getKoreanName().equals(value)) {
				return gu;
			}
		}
		throw new IllegalArgumentException("알 수 없는 Gu 값입니다: " + value);
	}
}