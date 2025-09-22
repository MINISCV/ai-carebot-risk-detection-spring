package com.project.domain;

import com.project.dto.MemberResponseDto;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	private String username;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private boolean enabled;
	
	@Builder
    public Member(String username, String password, Role role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }
	
	public void update(MemberResponseDto dto) {
        this.username = dto.username();
        this.role = dto.role();
        this.enabled = dto.enabled();
    }
}
