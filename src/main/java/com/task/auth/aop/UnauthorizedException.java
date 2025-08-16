package com.task.auth.aop;

public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException(String msg){ super(msg); }
}
