package com.example.jpanote.member.model.dto;

import lombok.*;

/**
 * packageName    : com.example.jpanote.member.model.dto
 * fileName       : LoginRequest
 * author         : pleaw
 * date           : 2023-10-27
 * description    : 사용자에게 데이터를 요청받기 위한 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-10-27        pleaw       최초 생성
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginRequest {
	private String memberEmail;
	private String memberPw;
}
