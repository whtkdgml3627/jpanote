package com.example.jpanote.member.service;

import com.example.jpanote.member.model.dto.CreateRequest;
import com.example.jpanote.member.model.dto.ReadResponse;
import com.example.jpanote.member.model.dto.UpdateRequest;
import com.example.jpanote.member.model.dto.UpdateResponse;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	//테스트 변수 선언
	private static final Long TEST_MEMBER_NO = 1L;
	private static final String TEST_MEMBER_EMAIL = "josh1@plea.kr";
	private static final String TEST_MEMBER_UPDATE_EMAIL = "plea@plea.kr";
	private static final String TEST_MEMBER_NAME = "조상희";
	private static final String TEST_MEMBER_PW = "1111";

	private CreateRequest createRequest;
	private UpdateRequest updateRequest;

	@BeforeEach
	public void setUp(){
		createRequest = CreateRequest.builder()
				.memberEmail(TEST_MEMBER_EMAIL)
				.memberName(TEST_MEMBER_NAME)
				.memberPw(TEST_MEMBER_PW)
				.build();

		updateRequest = UpdateRequest.builder()
				.id(TEST_MEMBER_NO)
				.memberEmail(TEST_MEMBER_UPDATE_EMAIL)
				.memberName(TEST_MEMBER_NAME)
				.memberPw(TEST_MEMBER_PW)
				.build();
	}


	@Test
	@DisplayName("회원 등록 Service 테스트")
	//@Transactional
	public void createMemberTest() throws Exception {
		//given + when
		memberService.createMember(createRequest);

		//then
		Assertions.assertNotNull(createRequest.getMemberEmail());
	}

	@Test
	@DisplayName("회원 조회 Service 테스트")
	@Transactional
	public void readMemberTest() throws Exception {
		//given + when
		ReadResponse response = memberService.readMember(TEST_MEMBER_NO);

		//then
		log.info("response" + response);
		Assertions.assertNotNull(response.getMemberEmail());
	}

	@Test
	@DisplayName("회원 수정 Service 테스트")
	@Transactional
	public void updateMemberTest() throws Exception {
		//given + when
		memberService.updateMember(updateRequest);

		//then
		Assertions.assertNotNull(updateRequest.getMemberEmail());
	}

	@Test
	@DisplayName("회원 탈퇴 Service 테스트")
	@Transactional
	public void removeMemberTest() throws Exception {
		//given + when
		memberService.removeMember(TEST_MEMBER_NO);

		//then
	}

}