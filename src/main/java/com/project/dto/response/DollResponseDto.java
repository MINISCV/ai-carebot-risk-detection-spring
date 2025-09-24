package com.project.dto.response;

import com.project.domain.senior.Doll;

public record DollResponseDto(
		String id,
		Long seniorId
) {
	public static DollResponseDto from(Doll doll) {
        if (doll == null) return null;
        if (doll.getSenior() == null)
        	return new DollResponseDto(doll.getId(), null);
        return new DollResponseDto(doll.getId(), doll.getSenior().getId());
    }
}
