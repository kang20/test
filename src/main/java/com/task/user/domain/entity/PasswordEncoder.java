package com.task.user.domain.entity;

public interface PasswordEncoder {
	String encode(String password);

	boolean matches(String password, String encodedHash);
}

