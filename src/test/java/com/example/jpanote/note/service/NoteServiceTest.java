package com.example.jpanote.note.service;

import com.example.jpanote.note.model.dto.CreateRequest;
import com.example.jpanote.note.model.dto.ListResponse;
import com.example.jpanote.note.model.dto.ReadResponse;
import com.example.jpanote.note.model.dto.UpdateRequest;
import com.example.jpanote.util.page.PagingRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Log4j2
class NoteServiceTest {

	@Autowired
	private NoteService noteService;

	//테스트 변수 선언
	private static final Long TEST_NOTE_NO = 1L;
	private static final String TEST_NOTE_TITLE = "제목 서비스 테스트입니다.";
	private static final String TEST_NOTE_CONTENT = "내용 서비스 테스트입니다.";
	private static final String TEST_NOTE_SENDER_EMAIL = "josh@plea.kr";
	private static final String TEST_NOTE_RECEIVER_EMAIL = "plea@plea.kr";

	private CreateRequest createRequest;
	private UpdateRequest updateReqeust;

	@BeforeEach
	public void setUp(){
		createRequest = CreateRequest.builder()
				.title(TEST_NOTE_TITLE)
				.cont(TEST_NOTE_CONTENT)
				.receiverEmail(TEST_NOTE_RECEIVER_EMAIL)
				.senderEmail(TEST_NOTE_SENDER_EMAIL)
				.build();

		updateReqeust = UpdateRequest.builder()
				.id(TEST_NOTE_NO)
				.title(TEST_NOTE_TITLE)
				.cont(TEST_NOTE_CONTENT)
				.build();
	}

	@Test
	@DisplayName("쪽지 생성 Service 테스트")
	@Transactional
	public void createNoteTest() throws Exception {
		//given + when
		noteService.createNote(createRequest);

		//then
		Assertions.assertNotNull(createRequest.getTitle());
	}

	@Test
	@DisplayName("쪽지 조회 Service 테스트")
	//@Transactional
	public void readNoteTest() throws Exception {
		//given + when
		ReadResponse readResponse = noteService.readNote(35L);

		//then
		log.info("readNote===" + readResponse);
		Assertions.assertNotNull(readResponse.getId());
	}

	@Test
	@DisplayName("쪽지 내용 수정 Service 테스트")
	@Transactional
	public void updateNoteTest() throws Exception {
		//given + when
		noteService.updateNote(updateReqeust);

		//then
		Assertions.assertNotNull(updateReqeust.getTitle());
	}

	@Test
	@DisplayName("쪽지 삭제 Service 테스트")
	@Transactional
	public void removeNoteTest() throws Exception {
		//given + when
		noteService.removeNote(TEST_NOTE_NO);

		//then
	}

	@Test
	@DisplayName("보낸 쪽지 리스트 Service 테스트")
	@Transactional
	public void senderNoteList() throws Exception {
		//given
		PagingRequest pagingRequest = PagingRequest.builder().build();

		//when
		List<ListResponse> senderList = noteService.getSenderList(TEST_NOTE_NO, pagingRequest);

		//then
		log.info("senderList=====" + senderList);
		Assertions.assertNotNull(senderList);
	}

	@Test
	@DisplayName("받은 쪽지 리스트 Service 테스트")
	@Transactional
	public void receiverNoteList() throws Exception {
		//given
		PagingRequest pagingRequest = PagingRequest.builder().build();

		//when
		List<ListResponse> receiverList = noteService.getReceiverList(TEST_NOTE_NO, pagingRequest);

		//then
		log.info("receiverList=====" + receiverList);
		Assertions.assertNotNull(receiverList);
	}
}