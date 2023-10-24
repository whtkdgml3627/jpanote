package com.example.jpanote.member.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
// 사용자에게 데이터를 반환하기 위한 DTO
public class UpdateResponse {

	private String memberEmail;
	private String memberName;

}
