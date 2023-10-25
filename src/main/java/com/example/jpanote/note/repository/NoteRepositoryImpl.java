package com.example.jpanote.note.repository;

import com.example.jpanote.member.model.entity.QMemberEntity;
import com.example.jpanote.note.model.dto.ListResponse;
import com.example.jpanote.note.model.entity.QNoteEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class NoteRepositoryImpl implements NoteRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ListResponse> recevierList(Long id, Pageable pageable) {
		return queryFactory.select(
					Projections.fields(
							ListResponse.class,
							QNoteEntity.noteEntity.id,
							QNoteEntity.noteEntity.title,
							QNoteEntity.noteEntity.cont,
							QNoteEntity.noteEntity.delFlag,
							QNoteEntity.noteEntity.readDt,
							QNoteEntity.noteEntity.readStat,
							QNoteEntity.noteEntity.member.memberEmail.as("senderEmail"),
							QNoteEntity.noteEntity.member2.memberEmail.as("receiverEmail")
					)
				)
				.from(QNoteEntity.noteEntity)
				.leftJoin(QMemberEntity.memberEntity)
				.on(QNoteEntity.noteEntity.member2.id.eq(QMemberEntity.memberEntity.id))
				.leftJoin(QMemberEntity.memberEntity)
				.on(QNoteEntity.noteEntity.member.id.eq(QMemberEntity.memberEntity.id))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.where(QNoteEntity.noteEntity.delFlag.eq(0).and(QNoteEntity.noteEntity.member2.id.eq(id)))
				.orderBy(QNoteEntity.noteEntity.createdDt.desc())
				.fetch()
		;
	}
}
