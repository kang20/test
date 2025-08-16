package com.task.user.domain.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.task.user.domain.entity.PasswordEncoder;


@Component
public class SecurePasswordEncoder implements PasswordEncoder {
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	public String encode(String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	@Override
	public boolean matches(String password, String encodedHash) {
		return bCryptPasswordEncoder.matches(password, encodedHash);
	}
}