package com.example.jpanote.note.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
// 사용자에게 데이터를 반환하기 위한 DTO
public class ReadResponse {
	private Long id;
	private String title;
	private String cont;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime readDt;
	private int readStat;
	private int delFlag;
	private String senderEmail;
	private String receiverEmail;
}
