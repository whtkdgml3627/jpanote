package com.example.jpanote.member.service;

import com.example.jpanote.exception.BadCredentialsException;
import com.example.jpanote.exception.DuplicateEmailException;
import com.example.jpanote.exception.MemberNotFoundException;
import com.example.jpanote.exception.WithdrawnMemberException;
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

	/**
	 * methodName : createMember
	 * author : Jo Sang Hee
	 * description : 회원 등록
	 * Create member create response.
	 *
	 * @param request the request
	 * @return the create response
	 */
	@Transactional
	public CreateResponse createMember(CreateRequest request){
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//회원 등록
		MemberEntity memberEntity = MemberEntity.createMember(request.getMemberEmail(),
																request.getMemberName(),
																passwordEncoder.encode(request.getMemberPw())
		);
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

	/**
	 * methodName : readMember
	 * author : Jo Sang Hee
	 * description : 회원 조회
	 * Read member read response.
	 *
	 * @param id the id
	 * @return the read response
	 */
	public ReadResponse readMember(Long id){
		//조회
		Optional<MemberEntity> findMember = memberRepository.findById(id);
		MemberEntity memberEntity = findMember.orElseThrow(() -> new MemberNotFoundException("조회 하신 회원이 존재하지 않습니다."));
		//탈퇴한 회원일 경우
		if(memberEntity.getDelFlag() != 0){
			throw new WithdrawnMemberException("탈퇴한 회원 입니다.");
		}
		//DTO 타입 으로 반환
		return ReadResponse.builder()
				.id(memberEntity.getId())
				.memberEmail(memberEntity.getMemberEmail())
				.memberName(memberEntity.getMemberName())
				.memberPw(memberEntity.getMemberPw())
				.build();
	}

	/**
	 * methodName : updateMember
	 * author : Jo Sang Hee
	 * description : 회원 수정
	 * Update member update response.
	 *
	 * @param request the request
	 * @return the update response
	 */
	@Transactional
	public UpdateResponse updateMember(UpdateRequest request){
		//중복 이메일 주소 확인
		MemberEntity existingMember = memberRepository.findByMemberEmail(request.getMemberEmail());
		if (existingMember != null && !existingMember.getId().equals(request.getId())) {
			//이미 존재하는 이메일 주소인 경우 예외 처리 또는 오류 처리
			throw new DuplicateEmailException("중복된 이메일 주소입니다.");
		}
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//조회
		Optional<MemberEntity> findMember = memberRepository.findById(request.getId());
		MemberEntity memberEntity = findMember.orElseThrow(() -> new MemberNotFoundException("조회 하신 회원이 존재하지 않습니다."));
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

	/**
	 * methodName : removeMember
	 * author : Jo Sang Hee
	 * description : 회원 탈퇴
	 * Remove member long.
	 *
	 * @param id the id
	 * @return the long
	 */
	@Transactional
	public Long removeMember(Long id){
		//조회
		// db에서 사용자를 delete 하는게 아니라 정보만 지우고 update 했기 때문에, findById 의 null check 만으로는 이미 탈퇴한 회원인지 구분할 수 없습니다.
		Optional<MemberEntity> findMember = Optional.ofNullable(memberRepository.findByIdAndDelFlag(id, 0));
		MemberEntity memberEntity = findMember.orElseThrow(() -> new MemberNotFoundException("조회 하신 회원이 존재하지 않습니다."));
		//삭제
		memberEntity.removeMember();
		memberRepository.save(memberEntity);
		return memberEntity.getId();
	}

	/**
	 * methodName : LoginMember
	 * author : Jo Sang Hee
	 * description : 로그인
	 * Login member long.
	 *
	 * @param request the request
	 * @return the long
	 */
	@Transactional
	public Long LoginMember(LoginRequest request){
		//이메일 주소 확인
		MemberEntity findMember = memberRepository.findByMemberEmail(request.getMemberEmail());

		if (findMember == null) {
			throw new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		//DB에 저장된 비밀번호 가져옴
		String userPassword = findMember.getMemberPw();

		//사용자가 입력한 비밀번호와 데이터베이스에서 가져온 암호화된 비밀번호를 대조
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean passwordMatches = passwordEncoder.matches(request.getMemberPw(), userPassword);
		log.info("passwordMatches======" + passwordMatches);
		if(!passwordMatches){
			throw new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		return findMember.getId();
	}

}
