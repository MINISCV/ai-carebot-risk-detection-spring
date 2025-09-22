package com.project.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.domain.Member;
import com.project.domain.Role;
import com.project.dto.MemberDto;
import com.project.persistence.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;

	@Transactional
	public void register(MemberDto memberDto) {
		if (memberRepository.existsById(memberDto.username()))
			throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
		
		Member member = Member.builder()
				.username(memberDto.username())
				.password(encoder.encode(memberDto.password()))
				.role(Role.ROLE_ADMIN)
				.enabled(true)
				.build();
		
		memberRepository.save(member);
	}
}