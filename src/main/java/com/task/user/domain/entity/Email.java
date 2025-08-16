package com.task.user.domain.entity;

import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Email(@Column(nullable = false,name = "email") String value) {
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

	public Email {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("Email cannot be null or blank");
		}
		if (!EMAIL_PATTERN.matcher(value).matches()) {
			throw new IllegalArgumentException("Invalid email format");
		}
	}
}
