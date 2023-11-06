package com.example.jpanote.note.controller;

import com.example.jpanote.note.model.dto.*;
import com.example.jpanote.note.service.NoteService;
import com.example.jpanote.util.page.PagingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
@Tag(name = "쪽지 관리", description = "쪽지 API Document")
public class NoteRestController {

	private final NoteService noteService;

	//쪽지 생성
	@PostMapping("/send")
	@Operation(summary = "쪽지 생성", description = "제목, 내용, 발신이메일, 수신이메일을 입력하여 쪽지를 생성 합니다.")
	/*@Parameters({
			@Parameter(name = "title", description = "쪽지 제목", example = "쪽지 발신 제목"),
			@Parameter(name = "cont", description = "쪽지 내용", example = "쪽지 발신 내용"),
			@Parameter(name = "senderEmail", description = "발신자 이메일", example = "plea@plea.kr"),
			@Parameter(name = "receiverEmail", description = "수신자 이메일", example = "josh@plea.kr")
	})*/
	public CreateResponse create(@ParameterObject @RequestBody CreateRequest request){
		return noteService.createNote(request);
	}
	
	//쪽지 조회
	@GetMapping("/read/{id}")
	public ReadResponse getById(@PathVariable Long id){
		return noteService.readNote(id);
	}

	//쪽지 내용 수정
	@PutMapping("/modify")
	public UpdateResponse update(@RequestBody UpdateRequest request){
		if(request.getId() == null) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		return noteService.updateNote(request);
	}

	//쪽지 삭제
	@DeleteMapping("/remove/{id}")
	public Map<String, Long> remove(@PathVariable Long id){
		Long noteId = noteService.removeNote(id);
		return Map.of("noteId", noteId);
	}

	//보낸 쪽지 리스트
	@GetMapping("/{id}/sender-list")
	@Operation(summary = "보낸 쪽지 리스트", description = "보낸 쪽지 리스트를 출력 합니다.")
	public List<ListResponse> getSenderList(@Parameter(description = "보낸 사람 이메일 아이디 번호") @PathVariable Long id,
	                                        @ParameterObject PagingRequest pagingRequest){
		return noteService.getSenderList(id, pagingRequest);
	}

	//받은 쪽지 리스트
	@GetMapping("/{id}/receiver-list")
	public List<ListResponse> getReceiverList(@PathVariable Long id, PagingRequest pagingRequest){
		return noteService.getReceiverList(id, pagingRequest);
	}

}
