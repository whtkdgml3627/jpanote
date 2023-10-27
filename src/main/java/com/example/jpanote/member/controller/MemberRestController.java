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

	/**
	 * methodName : create
	 * author : Jo Sang Hee
	 * description : 회원 등록
	 * Create create response.
	 *
	 * @param request the request
	 * @return the create response
	 */
	@PostMapping("/join")
	public CreateResponse create(@RequestBody CreateRequest request){
		return memberService.createMember(request);
	}

	/**
	 * methodName : getById
	 * author : Jo Sang Hee
	 * description : 회원 조회
	 * Get by id read response.
	 *
	 * @param id the id
	 * @return the read response
	 */
	@GetMapping("/my-page/{id}")
	public ReadResponse getById(@PathVariable Long id){
		return memberService.readMember(id);
	}

	/**
	 * methodName : update
	 * author : Jo Sang Hee
	 * description : 회원 수정
	 * Update update response.
	 *
	 * @param request the request
	 * @return the update response
	 */
	@PutMapping("/modify")
	public UpdateResponse update(@RequestBody UpdateRequest request){
		if(request.getId() == null) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		return memberService.updateMember(request);
	}

	/**
	 * methodName : remove
	 * author : Jo Sang Hee
	 * description : 회원 탈퇴
	 * Remove map.
	 *
	 * @param id the id
	 * @return the map
	 */
	@DeleteMapping("/leave/{id}")
	public Map<String, Long> remove(@PathVariable Long id){
		Long memberId = memberService.removeMember(id);
		return Map.of("memberId", memberId);
	}

	/**
	 * methodName : login
	 * author : Jo Sang Hee
	 * description : 로그인
	 * Login map.
	 *
	 * @param request the request
	 * @return the map
	 */
	@PostMapping("/login")
	public Map<String, Long> login(@RequestBody LoginRequest request){
		Long memberId = memberService.LoginMember(request);
		return Map.of("memberId", memberId);
	}

}
