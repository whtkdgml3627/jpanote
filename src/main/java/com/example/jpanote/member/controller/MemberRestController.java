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

	// RESTful API 원칙에 의하면 member 도메인에 대한 CRUD일때, join, modify 등의 동작은 POST, PUT같은 메소드로 구분하고, path에서는 제외합니다.
	/**
	 * methodName : create
	 * author : Jo Sang Hee
	 * description : 회원 등록
	 * Create create response.
	 *
	 * @param request the request
	 * @return the create response
	 */
	@PostMapping("")
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
	@GetMapping("/{id}")
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
	// RESTful 원칙에 의하면 modify 같은 동작은 PUT 메소드가 나타내고, path에서는 제외합니다.
	@PutMapping("")
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
	@DeleteMapping("/{id}")
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
