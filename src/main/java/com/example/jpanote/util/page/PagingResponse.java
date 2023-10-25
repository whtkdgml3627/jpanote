package com.example.jpanote.util.page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PagingResponse<E> {
	//출력할 List
	private List<E> list;
	//파라미터 받을 request
	private PagingRequest pagingRequest;
	//페이지 번호
	private int page;
	//사이즈
	private int size;
	//시작 번호
	private int start;
	//끝 번호
	private int end;

	//반환타입
	@Builder(builderMethodName = "withAll")
	public PagingResponse(
			List<E> list, PagingRequest pagingRequest
	){
		this.list = list;
		this.page = pagingRequest.getPage();
		this.size = pagingRequest.getSize();
		//페이징 계산
		//시작 번호
		this.start = ((int) (Math.ceil(this.page / 10.0) * 10) - 9);
		//끝 번호
		this.end = this.start + 9;
	}

}
