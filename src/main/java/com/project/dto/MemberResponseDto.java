package com.project.dto;

import com.project.domain.Role;

public record MemberResponseDto(String username, Role role, boolean enabled) {

}
