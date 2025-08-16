package com.task.chat.controller;

public record CreateChatRequest(
	String question,
	Boolean isStreaming,
	String model
) {
}
