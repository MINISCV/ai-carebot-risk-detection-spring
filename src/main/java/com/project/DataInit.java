package com.project;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.domain.member.Member;
import com.project.domain.member.Role;
import com.project.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInit implements ApplicationRunner {
	private final PasswordEncoder encoder;
	private final MemberRepository memberRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(" ========= 멤버 생성 =========");
		Member admin = Member.builder().username("admin").password(encoder.encode("admin")).role(Role.ROLE_ADMIN).enabled(true).build();
		Member member = Member.builder().username("member").password(encoder.encode("member")).role(Role.ROLE_MEMBER).enabled(true).build();
		memberRepository.save(admin);
		memberRepository.save(member);
		System.out.println(" ========= 생성 완료 =========");
	}
}
