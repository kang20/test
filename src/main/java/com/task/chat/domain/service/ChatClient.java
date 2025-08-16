package com.task.chat.domain.service;

import java.util.concurrent.CompletableFuture;

import com.task.chat.domain.dto.ChatQuestionDto;

public interface ChatClient {
	CompletableFuture<String> sendQuestion(ChatQuestionDto question);
}
