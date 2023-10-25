package com.example.jpanote.note.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ListResponse {
	private Long id;
	private String title;
	private String cont;
	private int delFlag;
	private LocalDateTime readDt;
	private int readStat;
	private String senderEmail;
	private String receiverEmail;
}
