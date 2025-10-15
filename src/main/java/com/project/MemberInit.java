package com.project;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.domain.member.Member;
import com.project.domain.member.Role;
import com.project.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberInit implements ApplicationRunner {
    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (memberRepository.count() == 0) {
            log.info(" ========= 멤버 생성 =========");
            Member admin = Member.builder().username("admin").password(encoder.encode("admin")).role(Role.ROLE_ADMIN).enabled(true).build();
            memberRepository.save(admin);
            log.info(" ========= 멤버 생성 완료 =========");
        }
    }
}