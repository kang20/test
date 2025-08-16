package com.task.chat.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatCreateRequest(
	@NotBlank
	String question,
	@NotBlank
	String answer
) {
}
