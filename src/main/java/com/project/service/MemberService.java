package com.project.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.Member;
import com.project.domain.Role;
import com.project.dto.MemberResponseDto;
import com.project.dto.SignUpRequestDto;
import com.project.persistence.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;

	@Transactional
	public Member register(SignUpRequestDto signUpRequestDto) {
		if (memberRepository.existsById(signUpRequestDto.username()))
			throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
		
		Member member = Member.builder()
				.username(signUpRequestDto.username())
				.password(encoder.encode(signUpRequestDto.password()))
				.role(Role.ROLE_ADMIN)
				.enabled(true)
				.build();
		
		memberRepository.save(member);
		
		return member;
	}
	
	@Transactional(readOnly = true)
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + username));
    }

    public Member updateMember(String username, MemberResponseDto requestDto) {
        Member member = findMemberByUsername(username);
        member.update(requestDto);
        return member;
    }

    public void deleteMember(String username) {
        if (!memberRepository.existsByUsername(username)) {
            throw new EntityNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
        memberRepository.deleteById(username);
    }
}