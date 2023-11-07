package com.example.jpanote.swagger;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

/**
 * packageName    : com.example.jpanote.swagger
 * fileName       : ApiDocumentResponse
 * author         : 조 상 희
 * date           : 2023-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-06       조 상 희         최초 생성
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "API 호출 성공")


})

public @interface ApiDocumentResponse {
}
