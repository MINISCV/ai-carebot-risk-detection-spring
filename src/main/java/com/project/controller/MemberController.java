package com.project.controller;

import java.net.URI;
import java.util.List;

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

import com.project.dto.MemberDto;
import com.project.dto.request.SignUpRequestDto;
import com.project.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<MemberDto> register(@Valid @RequestBody SignUpRequestDto requestDto) {
		MemberDto memberDto = memberService.register(requestDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{username}")
				.buildAndExpand(memberDto.username())
				.toUri();
		return ResponseEntity.created(location).body(memberDto);
	}
	
	@GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        List<MemberDto> members = memberService.findAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{username}")
    public ResponseEntity<MemberDto> getMember(@PathVariable String username) {
        MemberDto memberDto = memberService.findMemberByUsername(username);
        return ResponseEntity.ok(memberDto);
    }

    @PutMapping("/{username}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable String username, @RequestBody MemberDto requestDto) {
        MemberDto memberDto = memberService.updateMember(username, requestDto);
        return ResponseEntity.ok(memberDto);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteMember(@PathVariable String username) {
        memberService.deleteMember(username);
        return ResponseEntity.noContent().build();
    }
}