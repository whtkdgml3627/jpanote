package com.example.jpanote.note.model.entity;

import com.example.jpanote.member.model.entity.MemberEntity;
import com.example.jpanote.util.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_note")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"member", "member2"})
public class NoteEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id")
	private Long id;

//	쪽지 제목
	@Column(name = "note_title", nullable = false)
	private String title;

//	쪽지 내용
	@Column(name = "note_content", length = 1000, nullable = false)
	private String cont;

//	쪽지 수신 이메일
//	@Column(name = "receiver_id", nullable = false)
//	private Long receiverId;

//	쪽지 읽은 시간
	@Column(name = "read_date")
	private LocalDateTime readDt;

//	쪽지 읽은 여부
	@Column(name = "read_status", nullable = false, columnDefinition = "integer default 0")
	private int readStat;

//	삭제 여부
	@Column(name = "del_fl", nullable = false, columnDefinition = "integer default 0")
	private int delFlag;

//	연관관계 - 회원
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", referencedColumnName = "member_id")
	private MemberEntity member;

//	연관관계 - 회원
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id", referencedColumnName = "member_id")
	private MemberEntity member2;

	//쪽지 생성 메소드
	public static NoteEntity createNote(String title, String cont, MemberEntity reciverId, MemberEntity senderId){
		return NoteEntity.builder()
				.title(title)
				.cont(cont)
				.member2(reciverId)
				.member(senderId)
				.build();
	}

	//쪽지 읽은 상태 시간 업데이트
	public void changeStat(){
		this.readDt = LocalDateTime.now();
		this.readStat = 1;
	}

	//쪽지 내용 수정
	public void updateNote(String title, String cont){
		this.title = title;
		this.cont = cont;
	}

	//쪽지 삭제
	public void removeNote(){
		this.title = "";
		this.cont = "";
		this.delFlag = 1;
	}

}
