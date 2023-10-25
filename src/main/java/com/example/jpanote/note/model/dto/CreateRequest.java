package com.example.jpanote.note.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
// 사용자에게 데이터를 요청받기 위한 DTO
public class CreateRequest {
	private String title;
	private String cont;
	private String senderEmail;
	private String receiverEmail;
}
