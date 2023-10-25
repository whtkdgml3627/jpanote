package com.example.jpanote.note.repository;

import com.example.jpanote.note.model.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long>, NoteRepositoryCustom {
	//쪽지 조회
	@Query("select n from NoteEntity n left outer join fetch n.member m left outer join fetch n.member2 m2 where n.id = :id")
	Optional<NoteEntity> readOne(@Param("id") Long id);

	//보낸 쪽지함
	@Query("select n from NoteEntity n where n.member.id = :sender_id")
	Page<NoteEntity> senderList(@Param("sender_id") Long id, Pageable pageable);

	//받은 쪽지함
}
