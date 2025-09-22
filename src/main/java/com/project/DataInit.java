package com.project;

import java.time.LocalDate;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.domain.Doll;
import com.project.domain.Member;
import com.project.domain.Role;
import com.project.domain.Senior;
import com.project.domain.Sex;
import com.project.persistence.MemberRepository;
import com.project.persistence.SeniorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInit implements ApplicationRunner {
	private final PasswordEncoder encoder;
	private final MemberRepository memberRepository;
	private final SeniorRepository seniorRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(" ========= 멤버 생성 =========");
		Member admin = Member.builder().username("admin").password(encoder.encode("admin")).role(Role.ROLE_ADMIN).enabled(true).build();
		Member member = Member.builder().username("member").password(encoder.encode("member")).role(Role.ROLE_MEMBER).enabled(true).build();
		memberRepository.save(admin);
		memberRepository.save(member);
		System.out.println(" ========= 생성 완료 =========");
		
		System.out.println(" ========= 인형 및 시니어 생성 =========");
		for(int i = 1; i <= 200; i++) {
			Doll doll = Doll.builder().id("" + i).build();
			Senior senior = Senior.builder().name("시니어" + i).birthDate(LocalDate.now()).sex(Sex.MALE).build();
			senior.changeDoll(doll);
			seniorRepository.save(senior);
		}
		System.out.println(" ========= 생성 완료 =========");
	}
}
