package com.example.jpanote.note.controller;

import com.example.jpanote.note.model.dto.*;
import com.example.jpanote.note.service.NoteService;
import com.example.jpanote.util.page.PagingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
public class NoteRestController {

	private final NoteService noteService;

	//쪽지 생성
	@PostMapping("/send")
	public CreateResponse create(@RequestBody CreateRequest request){
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
	public List<ListResponse> getSenderList(@PathVariable Long id, PagingRequest pagingRequest){
		return noteService.getSenderList(id, pagingRequest);
	}

	//받은 쪽지 리스트
	@GetMapping("/{id}/receiver-list")
	public List<ListResponse> getReceiverList(@PathVariable Long id, PagingRequest pagingRequest){
		return noteService.getReceiverList(id, pagingRequest);
	}

}
