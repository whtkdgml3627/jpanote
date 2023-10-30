package com.example.jpanote.member.repository;

import com.example.jpanote.member.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {

	//email 체크
	MemberEntity findByMemberEmail (String memberEmail);

	MemberEntity findByIdAndDelFlag(Long id, Integer delFlag);
}
