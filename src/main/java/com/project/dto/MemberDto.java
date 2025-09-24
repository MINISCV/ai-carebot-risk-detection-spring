package com.project.dto;

import com.project.domain.member.Member;
import com.project.domain.member.Role;

public record MemberDto(
        String username,
        Role role,
        boolean enabled
) {
    public MemberDto(Member member) {
        this(
            member.getUsername(),
            member.getRole(),
            member.isEnabled()
        );
    }
}