package com.example.jpanote.util.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "페이지 번호, 리스트 사이즈 요구")
public class PagingRequest {
	//페이지 번호
	@Builder.Default
	@Schema(description = "페이지 번호를 입력 해 주세요.", example = "1")
	private int page = 1;
	//사이즈 크기
	@Builder.Default
	@Schema(description = "리스트 사이즈를 입력 해 주세요.", example = "10")
	private int size = 10;

}
