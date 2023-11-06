package com.example.jpanote.note.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(description = "쪽지 생성 요청")
// 사용자에게 데이터를 요청받기 위한 DTO
public class CreateRequest {
	@Schema(description = "쪽지 제목을 넣어 주세요", example = "쪽지 제목")
	private String title;
	@Schema(description = "쪽지 내용을 넣어 주세요", example = "쪽지 내용")
	private String cont;
	@Schema(description = "발신 이메일을 넣어 주세요", example = "josh@plea.kr")
	private String senderEmail;
	@Schema(description = "수신 이메일을 넣어 주세요", example = "plea@plea.kr")
	private String receiverEmail;
}
