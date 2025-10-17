package com.project.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.domain.member.Member;
import com.project.domain.member.Role;

public interface MemberRepository extends JpaRepository<Member, String>{
	Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);
    List<Member> findByRole(Role role);
}
