package com.task.chat.domain.implement;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.task.chat.domain.dto.ChatQuestionDto;
import com.task.chat.domain.service.ChatClient;

@Service
public class TestChatClient implements ChatClient {

	@Override
	public CompletableFuture<String> sendQuestion(ChatQuestionDto question) {
		return CompletableFuture.supplyAsync(() -> {;
			// 테스트 용 AI 요청
			try {
				Thread.sleep(1000); // Simulate delay
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return "This is a mock response to the question: " ;
		});
	}
}
