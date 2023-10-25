package com.example.jpanote.util.page;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PagingRequest {
	//페이지 번호
	@Builder.Default
	private int page = 1;
	//사이즈 크기
	@Builder.Default
	private int size = 10;

}
