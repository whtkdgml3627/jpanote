package com.example.jpanote.note.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(description = "쪽지 수정 요청")
public class UpdateRequest {
	@Schema(name = "번호", description = "쪽지 번호를 입력 해 주세요.", example = "1")
	private Long id;
	@Schema(name = "제목", description = "쪽지 제목을 입력 해 주세요.", example = "쪽지 제목")
	private String title;
	@Schema(name = "내용", description = "쪽지 내용을 입력 해 주세요.", example = "쪽지 내용")
	private String cont;
}
