package com.example.jpanote.member.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReadResponse {
	private Long id;
	private String memberEmail;
	private String memberName;
	private String memberPw;
}
