package com.task.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorController {
	@ExceptionHandler(DomainExcecption.class)
	public ResponseEntity<String> handleServiceException(DomainExcecption exception) {
		return ResponseEntity
			.status(exception.getStatus())
			.body(exception.getMessage());
	}

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		log.warn("MethodArgumentNotValidException : {}", errorMessage);

		return ResponseEntity
			.status(BAD_REQUEST)
			.body(errorMessage);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception exception) {
		log.error("Exception : {}", exception.getMessage(), exception);
		return ResponseEntity
			.status(INTERNAL_SERVER_ERROR)
			.body("서버 측 에러입니다.");
	}
}
