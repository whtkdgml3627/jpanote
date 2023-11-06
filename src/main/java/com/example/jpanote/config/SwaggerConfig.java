package com.example.jpanote.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.example.jpanote.config
 * fileName       : SwaggerConfig
 * author         : 조 상 희
 * date           : 2023-11-03
 * description    : Swagger springdoc-ui 구성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-03       조 상 희         최초 생성
 */

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI(){
		Info info = new Info()
				.title("쪽지 시스템 API")
				.version("V1")
				.description("쪽지 시스템 개인 프로젝트 API 문서 입니다.");

		return new OpenAPI()
				.components(new Components())
				.info(info);
	}

}
