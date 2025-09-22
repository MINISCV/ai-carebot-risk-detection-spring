package com.project.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.domain.Member;
import com.project.dto.MemberResponseDto;
import com.project.dto.SignUpRequestDto;
import com.project.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
		try {
			Member member = memberService.register(signUpRequestDto);
			MemberResponseDto responseDto = new MemberResponseDto(member.getUsername(), member.getRole(),
					member.isEnabled());
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{username}")
					.buildAndExpand(member.getUsername())
					.toUri();
			return ResponseEntity.created(location).body(responseDto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 username 입니다.");
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("회원가입 중 오류가 발생했습니다.");
		}
	}
	
	@GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> members = memberService.findAllMembers().stream()
                .map(member -> new MemberResponseDto(member.getUsername(), member.getRole(), member.isEnabled()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{username}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable String username) {
        Member member = memberService.findMemberByUsername(username);
        MemberResponseDto responseDto = new MemberResponseDto(member.getUsername(), member.getRole(), member.isEnabled());
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{username}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable String username, @RequestBody MemberResponseDto requestDto) {
        Member updatedMember = memberService.updateMember(username, requestDto);
        MemberResponseDto responseDto = new MemberResponseDto(updatedMember.getUsername(), updatedMember.getRole(), updatedMember.isEnabled());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteMember(@PathVariable String username) {
        memberService.deleteMember(username);
        return ResponseEntity.noContent().build();
    }
}