package com.example.jpanote.member.service;

import com.example.jpanote.exception.DuplicateEmailException;
import com.example.jpanote.exception.UserNotFoundException;
import com.example.jpanote.member.model.dto.*;
import com.example.jpanote.member.model.entity.MemberEntity;
import com.example.jpanote.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class MemberService {

	private final MemberRepository memberRepository;

	//회원 등록
	@Transactional
	public CreateResponse createMember(CreateRequest request){
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//회원 등록
		MemberEntity memberEntity = MemberEntity.createMember(request.getMemberEmail(), request.getMemberName(), passwordEncoder.encode(request.getMemberPw()));
		//중복 이메일 주소 확인
		MemberEntity existingMember = memberRepository.findByMemberEmail(memberEntity.getMemberEmail());
		if (existingMember != null) {
			//이미 존재하는 이메일 주소인 경우 예외 처리 또는 오류 처리
			throw new DuplicateEmailException("중복된 이메일 주소입니다.");
		}
		//저장
		MemberEntity entity = memberRepository.save(memberEntity);
		//DTO 타입 으로 반환
		return CreateResponse.builder()
				.memberEmail(entity.getMemberEmail())
				.memberName(entity.getMemberName())
				.build();
	}

	//회원 조회
	public ReadResponse readMember(Long id){
		//조회
		MemberEntity memberEntity = memberRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("조회 하신 회원이 존재하지 않습니다."));
		if(memberEntity.getDelFlag() != 0){
			throw new UserNotFoundException("탈퇴한 회원 입니다.");
		}
		//DTO 타입 으로 반환
		return ReadResponse.builder()
				.id(memberEntity.getId())
				.memberEmail(memberEntity.getMemberEmail())
				.memberName(memberEntity.getMemberName())
				.memberPw(memberEntity.getMemberPw())
				.build();
	}

	//회원 수정
	@Transactional
	public UpdateResponse updateMember(UpdateRequest request){
		//중복 이메일 주소 확인
		MemberEntity existingMember = memberRepository.findByMemberEmail(request.getMemberEmail());
//		log.info("==============================================");
//		log.info(existingMember);
		if (existingMember != null && !existingMember.getId().equals(request.getId())) {
			//이미 존재하는 이메일 주소인 경우 예외 처리 또는 오류 처리
			throw new DuplicateEmailException("중복된 이메일 주소입니다.");
		}
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//조회
		MemberEntity memberEntity = memberRepository.findById(request.getId())
				.orElseThrow(() -> new NullPointerException("조회 하신 회원이 존재하지 않습니다."));
//		log.info("----------------------------------------------");
		//회원정보 수정
		memberEntity.updateMember(
				request.getMemberEmail(), request.getMemberName(), passwordEncoder.encode(request.getMemberPw())
		);
		if(memberEntity.getMemberEmail().equals(request.getMemberEmail())){
			//동일한 이메일 주소일 경우 예외 처리
			throw new DuplicateEmailException("동일한 이메일 주소입니다.");
		}
		//저장
		memberRepository.save(memberEntity);
		//DTO 타입 으로 반환
		return UpdateResponse.builder()
				.memberEmail(memberEntity.getMemberEmail())
				.memberName(memberEntity.getMemberName())
				.build();
	}

	//회원 탈퇴
	@Transactional
	public Long removeMember(Long id){
		//조회
		MemberEntity memberEntity = memberRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("조회 하신 회원이 존재하지 않습니다."));
		//삭제
		memberEntity.removeMember();
		memberRepository.save(memberEntity);
		return memberEntity.getId();
	}

}
