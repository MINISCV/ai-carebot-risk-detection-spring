package com.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.member.Member;
import com.project.domain.member.Role;
import com.project.dto.MemberDto;
import com.project.dto.request.SignUpRequestDto;
import com.project.persistence.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;

	@Transactional
	public MemberDto register(SignUpRequestDto requestDto) {
		if (memberRepository.existsById(requestDto.username()))
			throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
		
		Member member = Member.builder()
				.username(requestDto.username())
				.password(encoder.encode(requestDto.password()))
				.role(Role.ROLE_ADMIN)
				.enabled(true)
				.build();
		
		memberRepository.save(member);
		
		return new MemberDto(member);
	}
	
	@Transactional(readOnly = true)
    public List<MemberDto> findAllMembers() {
        List <Member> members = memberRepository.findAll();
        return members.stream()
        		.map(MemberDto::new)
        		.collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDto findMemberByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + username));
        return new MemberDto(member);
    }

    @Transactional
    public MemberDto updateMember(String username, MemberDto requestDto) {
        Member member = memberRepository.findByUsername(username)
        		.orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + username));;
        member.update(requestDto.role(), requestDto.enabled());
        return new MemberDto(member);
    }

    @Transactional
    public void deleteMember(String username) {
        if (!memberRepository.existsByUsername(username))
            throw new EntityNotFoundException("사용자를 찾을 수 없습니다: " + username);
        memberRepository.deleteById(username);
    }
}