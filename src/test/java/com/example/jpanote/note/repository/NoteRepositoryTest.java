package com.example.jpanote.note.repository;

import com.example.jpanote.exception.DeletedNoteException;
import com.example.jpanote.exception.NoteNotFoundException;
import com.example.jpanote.exception.WithdrawnMemberException;
import com.example.jpanote.member.model.entity.MemberEntity;
import com.example.jpanote.member.repository.MemberRepository;
import com.example.jpanote.note.model.dto.ListResponse;
import com.example.jpanote.note.model.dto.ReadResponse;
import com.example.jpanote.note.model.entity.NoteEntity;
import com.example.jpanote.util.page.PagingRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class NoteRepositoryTest {

	@Autowired(required = false)
	private NoteRepository noteRepository;

	@Autowired(required = false)
	private MemberRepository memberRepository;

	//테스트 변수 선언
	private static final Long TEST_NOTE_NO = 1L;
	private static final String TEST_NOTE_TITLE = "쪽지 제목";
	private static final String TEST_NOTE_CONTENT = "쪽지 내용 테스트 입니다.";
	private static final String TEST_MEMBER_SEND_EMAIL = "josh@plea.kr";
	private static final String TEST_MEMBER_SEND_EMAIL2 = "whtkdgml3627@plea.kr";
	private static final String TEST_MEMBER_RECEIVE_EMAIL = "plea@plea.kr";

	@Test
	@DisplayName("쪽지 생성 Repository 테스트")
	@Transactional
	public void createNoteTest() throws Exception {
		//given
		MemberEntity memberSender = memberRepository.findByMemberEmail(TEST_MEMBER_SEND_EMAIL);

		//수신 이메일이 없는 경우 체크
		MemberEntity memberReceive = memberRepository.findByMemberEmail(TEST_MEMBER_RECEIVE_EMAIL);
		if (memberReceive == null) {
			// 존재하지 않는 이메일 주소인 경우 예외 처리 또는 오류 처리
			throw new WithdrawnMemberException("잘못된 이메일 주소 입니다.");
		}

		//when
		//Entity에 값을 저장
		NoteEntity noteEntity = NoteEntity.createNote(
				TEST_NOTE_TITLE, TEST_NOTE_CONTENT, memberReceive, memberSender
		);

		//when
		Long noteId = noteRepository.save(noteEntity).getId();

		Optional<NoteEntity> findNote = noteRepository.findById(noteId);

		//then
		Assertions.assertNotNull(findNote);
	}

	@Test
	@DisplayName("쪽지 조회 Repository 테스트")
	@Transactional
	public void readNoteTest() throws Exception {
		//given
		//Optional<NoteEntity> findNote = noteRepository.findById(TEST_NOTE_NO);
		Optional<NoteEntity> findNote = noteRepository.readOne(TEST_NOTE_NO);
		NoteEntity noteEntity = findNote.orElseThrow(() -> new NoteNotFoundException("해당 번호의 쪽지가 없습니다."));
		//when
		//삭제된 쪽지일 경우
		if(noteEntity.getDelFlag() != 0){
			throw new DeletedNoteException("삭제된 쪽지 입니다.");
		}

		//읽지 않은 상태일 때 읽음 상태로 변경
		if(noteEntity.getReadStat() == 0){
			noteEntity.changeStat();
			noteRepository.save(noteEntity);
		}

		//entity -> dto로 변환
		ReadResponse readResponse = ReadResponse.builder()
				.id(noteEntity.getId())
				.title(noteEntity.getTitle())
				.cont(noteEntity.getCont())
				.readDt(noteEntity.getReadDt())
				.readStat(noteEntity.getReadStat())
				.delFlag(noteEntity.getDelFlag())
				.senderEmail(noteEntity.getMember().getMemberEmail())
				.receiverEmail(noteEntity.getMember2().getMemberEmail())
				.build();
		
		log.info("noteEntity=================="+readResponse);

		//then
		Assertions.assertNotNull(noteEntity);
	}

	@Test
	@DisplayName("쪽지 내용 수정 Repository 테스트")
	@Transactional
	public void updateNoteTest() throws Exception {
		//given
		Optional<NoteEntity> findNote = noteRepository.findById(TEST_NOTE_NO);
		NoteEntity noteEntity = findNote.orElseThrow(() -> new NoteNotFoundException("해당 번호의 쪽지가 없습니다."));

		//when
		noteEntity.updateNote(
				TEST_NOTE_TITLE, TEST_NOTE_CONTENT
		);
		noteRepository.save(noteEntity);

		//then
		Assertions.assertNotNull(noteEntity);
	}

	@Test
	@DisplayName("쪽지 삭제 Repository 테스트")
	@Transactional
	public void removeNoteTest() throws Exception {
		//given
		Optional<NoteEntity> findNote = noteRepository.findById(TEST_NOTE_NO);
		NoteEntity noteEntity = findNote.orElseThrow(() -> new NoteNotFoundException("해당 번호의 쪽지가 없습니다."));

		//when
		noteEntity.removeNote();
		noteRepository.save(noteEntity);

		//then
		Assertions.assertNotNull(noteEntity);
	}

	@Test
	@DisplayName("보낸 쪽지 리스트 Repository 테스트")
	@Transactional
	public void senderListTest() throws Exception {
		//given
		PagingRequest pagingRequest = new PagingRequest();
		Pageable pageable = PageRequest.of(pagingRequest.getPage() - 1, pagingRequest.getSize(), Sort.by("id").descending());

		//when
		Page<NoteEntity> getList = noteRepository.senderList(2L, pageable);

		log.info("--------------------------------------------------");
		log.info("list" + getList);

		//then
		Assertions.assertNotNull(getList);
	}

	@Test
	@DisplayName("받은 쪽지 리스트 Repository 테스트")
	@Transactional
	public void receiverListTest() throws Exception {
		//given
		PagingRequest pagingRequest = new PagingRequest();
		Pageable pageable = PageRequest.of(pagingRequest.getPage() - 1, pagingRequest.getSize(), Sort.by("id").descending());

		//when
		List<ListResponse> getList = noteRepository.recevierList(2L, pageable);

		log.info("--------------------------------------------------");
		log.info("list" + getList);

		//then
		Assertions.assertNotNull(getList);
	}

}