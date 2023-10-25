package com.example.jpanote.member.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
// 사용자에게 데이터를 반환하기 위한 DTO
public class ReadResponse {
	private Long id;
	private String memberEmail;
	private String memberName;
	private String memberPw;
}
