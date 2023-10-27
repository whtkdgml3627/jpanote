package com.example.jpanote.note.service;

import com.example.jpanote.exception.DeletedNoteException;
import com.example.jpanote.exception.NoteNotFoundException;
import com.example.jpanote.exception.WithdrawnMemberException;
import com.example.jpanote.member.model.entity.MemberEntity;
import com.example.jpanote.member.repository.MemberRepository;
import com.example.jpanote.note.model.dto.*;
import com.example.jpanote.note.model.entity.NoteEntity;
import com.example.jpanote.note.repository.NoteRepository;
import com.example.jpanote.util.page.PagingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class NoteService {

	private final NoteRepository noteRepository;
	private final MemberRepository memberRepository;

	//쪽지 생성
	@Transactional
	public CreateResponse createNote(CreateRequest request){
		//발신 이메일
		MemberEntity memberSender = memberRepository.findByMemberEmail(request.getSenderEmail());
		//수신 이메일
		MemberEntity memberReceiver = memberRepository.findByMemberEmail(request.getReceiverEmail());
		//수신 이메일이 존재하지 않을 경우
		if (memberReceiver == null) {
			// 존재하지 않는 이메일 주소인 경우 예외 처리 또는 오류 처리
			throw new WithdrawnMemberException("잘못된 이메일 주소 입니다.");
		}
		//저장
		NoteEntity noteEntity = NoteEntity.createNote(
				request.getTitle(), request.getCont(), memberReceiver, memberSender
		);
		noteRepository.save(noteEntity);
		//DTO 타입으로 반환
		return CreateResponse.builder()
				.id(noteEntity.getId())
				.title(noteEntity.getTitle())
				.cont(noteEntity.getCont())
				.senderEmail(noteEntity.getMember().getMemberEmail())
				.receiverEmail(noteEntity.getMember2().getMemberEmail())
				.build();
	}
	
	//쪽지 조회
	@Transactional
	public ReadResponse readNote(Long id){
		//쪽지 조회
		Optional<NoteEntity> findNote = noteRepository.readOne(id);
		NoteEntity noteEntity = findNote.orElseThrow(() -> new NoteNotFoundException("해당 번호의 쪽지가 없습니다."));
		//삭제된 쪽지일 경우
		if(noteEntity.getDelFlag() != 0){
			throw new DeletedNoteException("삭제된 쪽지 입니다.");
		}
		//읽지 않은 상태일 때 읽음 상태로 변경
		if(noteEntity.getReadStat() == 0){
			noteEntity.changeStat();
			noteRepository.save(noteEntity);
		}
		//DTO 타입으로 반환
		return ReadResponse.builder()
				.id(noteEntity.getId())
				.title(noteEntity.getTitle())
				.cont(noteEntity.getCont())
				.readDt(noteEntity.getReadDt())
				.readStat(noteEntity.getReadStat())
				.delFlag(noteEntity.getDelFlag())
				.senderEmail(noteEntity.getMember().getMemberEmail())
				.receiverEmail(noteEntity.getMember2().getMemberEmail())
				.build();
	}

	//쪽지 내용 수정
	@Transactional
	public UpdateResponse updateNote(UpdateRequest reqeust){
		//쪽지 조회
		NoteEntity noteEntity = findNote(reqeust.getId());
		//수정
		noteEntity.updateNote(
				reqeust.getTitle(), reqeust.getCont()
		);
		//저장
		noteRepository.save(noteEntity);
		//DTO 타입으로 반환
		return UpdateResponse.builder()
				.id(noteEntity.getId())
				.title(noteEntity.getTitle())
				.cont(noteEntity.getCont())
				.build();
	}

	//쪽지 삭제
	@Transactional
	public Long removeNote(Long id){
		//쪽지 조회
		NoteEntity noteEntity = findNote(id);
		//삭제
		noteEntity.removeNote();
		//저장
		return noteRepository.save(noteEntity).getId();
	}

	//보낸 쪽지 리스트
	public List<ListResponse> getSenderList(Long id, PagingRequest pagingRequest){
		//페이징 처리
		Pageable pageable = PageRequest.of(pagingRequest.getPage() - 1
												, pagingRequest.getSize()
												, Sort.by("id").descending());
		//페이지 타입 으로
		Page<NoteEntity> entityPage = noteRepository.senderList(id, pageable);
		//DTO 타입으로 반환
		return entityPage.getContent().stream()
				.map(noteEntity -> ListResponse.builder()
						.id(noteEntity.getId())
						.title(noteEntity.getTitle())
						.cont(noteEntity.getCont())
						.readDt(noteEntity.getReadDt())
						.readStat(noteEntity.getReadStat())
						.senderEmail(noteEntity.getMember().getMemberEmail())
						.receiverEmail(noteEntity.getMember2().getMemberEmail())
						.build())
				.collect(Collectors.toList());
	}

	//받은 쪽지 리스트
	public List<ListResponse> getReceiverList(Long id, PagingRequest pagingRequest){
		//페이징 처리
		Pageable pageable = PageRequest.of(pagingRequest.getPage() - 1, pagingRequest.getSize(), Sort.by("id").descending());

		//DTO 타입으로 반환
		return noteRepository.recevierList(id, pageable);
	}

	//쪽지 조회 공통
	public NoteEntity findNote(Long id){
		Optional<NoteEntity> findNote = noteRepository.findById(id);
		return findNote.orElseThrow(() -> new NoteNotFoundException("해당 번호의 쪽지가 없습니다."));
	}
	

}
