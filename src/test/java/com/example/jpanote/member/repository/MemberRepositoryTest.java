package com.example.jpanote.member.repository;

import com.example.jpanote.exception.DuplicateEmailException;
import com.example.jpanote.exception.MemberNotFoundException;
import com.example.jpanote.exception.WithdrawnMemberException;
import com.example.jpanote.member.model.entity.MemberEntity;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class MemberRepositoryTest {

	@Autowired(required = false)
	private MemberRepository memberRepository;

	//테스트 변수 선언
	private static final Long TEST_MEMBER_NO = 1L;
	private static final String TEST_MEMBER_EMAIL = "josh@plea.kr";
	private static final String TEST_MEMBER_UPDATE_EMAIL = "plea@plea.kr";
	private static final String TEST_MEMBER_NAME = "조상희";
	private static final String TEST_MEMBER_PW = "1111";

	@Test
	@DisplayName("회원 등록 Repository 테스트")
	//@Transactional
	public void createMemberTest() throws Exception {
		//given
		//Entity에 값을 지정
		MemberEntity memberEntity = MemberEntity.createMember(
				TEST_MEMBER_EMAIL, TEST_MEMBER_NAME, TEST_MEMBER_PW
		);

		//when
		// 중복 이메일 주소 확인
		MemberEntity existingMember = memberRepository.findByMemberEmail(memberEntity.getMemberEmail());
		if (existingMember != null) {
			// 이미 존재하는 이메일 주소인 경우 예외 처리 또는 오류 처리
			throw new DuplicateEmailException("중복된 이메일 주소입니다.");
		}

		//repository로 저장
		Long mno = memberRepository.save(memberEntity).getId();
		//저장한 email 찾기
		Optional<MemberEntity> findMember = memberRepository.findById(mno);

		//then
		Assertions.assertNotNull(findMember);
	}

	@Test
	@DisplayName("회원 조회 Repository 테스트")
	@Transactional
	public void readMemberTest() throws Exception {
		//given
		Optional<MemberEntity> findMember = memberRepository.findById(TEST_MEMBER_NO);
		MemberEntity memberEntity = findMember.orElseThrow(() -> new MemberNotFoundException("조회 하신 회원이 존재하지 않습니다."));

		//when
		if(memberEntity.getDelFlag() != 0){
			throw new WithdrawnMemberException("탈퇴한 회원 입니다.");
		}

		//then
		Assertions.assertNotNull(memberEntity);
	}

	@Test
	@DisplayName("회원 수정 Repository 테스트")
	@Transactional
	public void updateMemberTest() throws Exception {
		//given
		Optional<MemberEntity> findMember = memberRepository.findById(TEST_MEMBER_NO);
		MemberEntity memberEntity = findMember.orElseThrow(() -> new MemberNotFoundException("조회 하신 회원이 존재하지 않습니다."));

		//when
		//데이터 수정
		memberEntity.updateMember(
				TEST_MEMBER_UPDATE_EMAIL, TEST_MEMBER_NAME, TEST_MEMBER_PW
		);
		// 중복 이메일 주소 확인
		MemberEntity existingMember = memberRepository.findByMemberEmail(memberEntity.getMemberEmail());
		if (existingMember != null) {
			// 이미 존재하는 이메일 주소인 경우 예외 처리 또는 오류 처리
			throw new DuplicateEmailException("중복된 이메일 주소입니다.");
		}
		//repository로 저장
		memberRepository.save(memberEntity);

		//then
		Assertions.assertNotNull(memberEntity);
	}

	@Test
	@DisplayName("회원 탈퇴 Repository 테스트")
	@Transactional
	public void removeMemberTest() throws Exception {
		//given
		Optional<MemberEntity> findMember = memberRepository.findById(TEST_MEMBER_NO);
		MemberEntity memberEntity = findMember.orElseThrow(() -> new MemberNotFoundException("조회 하신 회원이 존재하지 않습니다."));

		//when
		//삭제
		//memberRepository.deleteById(TEST_MEMBER_NO);
		memberEntity.removeMember();
		memberRepository.save(memberEntity);

		//then
		Assertions.assertNotNull(memberEntity);
	}

}