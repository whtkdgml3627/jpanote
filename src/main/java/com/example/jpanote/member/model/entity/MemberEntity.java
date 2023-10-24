package com.example.jpanote.member.model.entity;

import com.example.jpanote.util.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_member")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

//	email을 unique로 설정
	@Column(name = "member_email", nullable = false, unique = true)
	private String memberEmail;

//	사용자 이름
	@Column(name = "member_name", nullable = false)
	private String memberName;

//	사용자 비밀번호
	@Column(name = "member_pw", nullable = false)
	private String memberPw;

//	삭제 여부
	@Column(name = "del_fl", nullable = false, columnDefinition = "integer default 0")
	private int delFlag;


	//회원 생성 메소드
	public static MemberEntity createMember(String memberEmail, String memberName, String memberPw){
		return MemberEntity.builder()
				.memberEmail(memberEmail)
				.memberName(memberName)
				.memberPw(memberPw)
				.build();
	}

	//회원 수정 메소드
	public void updateMember(String memberEmail, String memberName, String memberPw){
		this.memberEmail = memberEmail;
		this.memberName = memberName;
		this.memberPw = memberPw;
	}

	//회원 삭제 메소드
	public void removeMember(){
		this.memberName = "";
		this.memberPw = "";
		this.delFlag = 1;
	}


}
