package com.task.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class DomainExcecption extends RuntimeException {
	private final HttpStatus status;

	private DomainExcecption(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public static DomainExcecption of(HttpStatus status, String message) {
		return new DomainExcecption(status, message);
	}
}
