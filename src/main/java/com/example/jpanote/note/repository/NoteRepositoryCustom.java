package com.example.jpanote.note.repository;

import com.example.jpanote.note.model.dto.ListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteRepositoryCustom {

	List<ListResponse> recevierList(Long id, Pageable pageable);
}
