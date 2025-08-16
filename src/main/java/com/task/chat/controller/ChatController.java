package com.task.chat.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.auth.aop.Auth;
import com.task.chat.domain.service.ChatService;

import com.task.auth.aop.AuthContext;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;

	@Auth
	@PostMapping("/chat")
	public String createChat(@RequestBody CreateChatRequest request) throws ExecutionException, InterruptedException {
		Long userId = AuthContext.current().id();
		return chatService.createChat(userId,request.question());
	}

	@Auth
	@DeleteMapping("/thread")
	public void deleteChat(@RequestParam Long threadId) throws ExecutionException, InterruptedException {
		Long userId = AuthContext.current().id();
		chatService.deleteChatThread(userId, threadId);
	}
}
