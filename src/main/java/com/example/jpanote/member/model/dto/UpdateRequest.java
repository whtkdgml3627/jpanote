package com.example.jpanote.member.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
// 사용자에게 데이터를 요청받기 위한 DTO
public class UpdateRequest {
	private Long id;
	private String memberEmail;
	private String memberName;
	private String memberPw;
}
