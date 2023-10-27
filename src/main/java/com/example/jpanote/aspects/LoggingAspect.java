package com.example.jpanote.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	private LocalDateTime startTime;

	@Before("execution(* *..controller.*.*(..))")
	public void logRequest(JoinPoint joinPoint) {
		//실행 메소드명
		String methodName = joinPoint.getSignature().toShortString();
		//처리 시간을 위한 시간 측정
		startTime = LocalDateTime.now();
		LOGGER.debug("@Before ######################## Calling method: " + methodName + "##############################");
		//실행 시간
		LOGGER.debug(this.localDate());
		LOGGER.debug("Calling method: " + methodName);
		LOGGER.debug("Parameter: " + Arrays.toString(joinPoint.getArgs()));
		LOGGER.debug("##############################################################################");
	}

	@AfterReturning(value = "execution(* *..controller.*.*(..))", returning = "returnValue")
	public void logResponse(JoinPoint joinPoint, Object returnValue) {
		String methodName = joinPoint.getSignature().toShortString();
		Duration duration = Duration.between(startTime, LocalDateTime.now());
		LOGGER.debug("@AfterReturning #################### Calling method: " + methodName + "##############################");
		LOGGER.debug("Milliseconds: " + String.valueOf(duration.toMillis()));
		LOGGER.debug("Calling method: " + methodName);
		LOGGER.debug("Return Value: " + returnValue.toString());
		LOGGER.debug("#####################################################################################");
	}

	@AfterThrowing(value = "execution(* *..controller.*.*(..))", throwing = "ex")
	public void logException(JoinPoint joinPoint, Exception ex) {
		String methodName = joinPoint.getSignature().toShortString();

		LOGGER.debug("@AfterThrowing #################### Calling method: " + methodName + "##############################");
		LOGGER.debug(this.localDate());
		LOGGER.debug("Calling method: " + methodName);
		LOGGER.debug("Exception: " + ex.getMessage());
		LOGGER.debug("#####################################################################################");
	}

	@AfterThrowing(value = "execution(* *..repository.*.*(..))", throwing = "ex")
	public void logDataException(JoinPoint joinPoint, Exception ex) {
		String methodName = joinPoint.getSignature().toShortString();

		LOGGER.debug("@AfterThrowing #################### Calling method: " + methodName + "##############################");
		LOGGER.debug(this.localDate());
		LOGGER.debug("Calling method: " + methodName);
		LOGGER.debug("Exception: " + ex.getMessage());
		LOGGER.debug("#####################################################################################");
	}

	private String localDate(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
