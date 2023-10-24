package com.example.jpanote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JpanoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpanoteApplication.class, args);
	}

}
