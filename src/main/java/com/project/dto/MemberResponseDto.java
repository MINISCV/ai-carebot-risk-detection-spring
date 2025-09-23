package com.project.dto;

import com.project.domain.Member;
import com.project.domain.Role;

public record MemberResponseDto(
        String username,
        Role role,
        boolean enabled
) {
    public MemberResponseDto(Member member) {
        this(
            member.getUsername(),
            member.getRole(),
            member.isEnabled()
        );
    }
}