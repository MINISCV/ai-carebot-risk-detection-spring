package com.project.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.domain.Member;
import com.project.domain.Role;
import com.project.persistence.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;
	
	@Transactional
	public void register(Member member) {
		if(memberRepository.existsById(member.getUsername()))
			throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
		member.setEnabled(true);
		member.setPassword(encoder.encode(member.getPassword()));
		member.setRole(Role.ROLE_ADMIN);
		memberRepository.save(member);
	}
}