package com.task.auth.aop;

public class ForbiddenException extends RuntimeException {
	public ForbiddenException(String msg){ super(msg); }
}
