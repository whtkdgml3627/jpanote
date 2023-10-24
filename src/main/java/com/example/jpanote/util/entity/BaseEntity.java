package com.example.jpanote.util.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(value = { AuditingEntityListener.class })
public abstract class BaseEntity {

//	최초 등록 날짜 등록
	@CreatedDate
	@Column(name = "create_date", updatable = false)
	private LocalDateTime createdDt;

//	수정 날짜 업데이트
	@LastModifiedDate
	@Column(name = "modifed_date")
	private LocalDateTime modifedDt;

}
