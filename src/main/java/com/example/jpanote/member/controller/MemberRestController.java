package com.example.jpanote.member.controller;

import antlr.StringUtils;
import com.example.jpanote.member.model.dto.*;
import com.example.jpanote.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberRestController {

	private final MemberService memberService;

	//회원 등록
	@PostMapping("/join")
	public CreateResponse create(@RequestBody CreateRequest request){
		return memberService.createMember(request);
	}

	//회원 조회
	@GetMapping("/my-page/{id}")
	public ReadResponse getById(@PathVariable Long id){
		return memberService.readMember(id);
	}

	//회원 수정
	@PutMapping("/modify")
	public UpdateResponse update(@RequestBody UpdateRequest request){
		if(request.getId() == null) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		return memberService.updateMember(request);
	}

	@DeleteMapping("/leave/{id}")
	public Map<String, Long> remove(@PathVariable Long id){
		Long memberId = memberService.removeMember(id);
		return Map.of("memberId", memberId);
	}



}
