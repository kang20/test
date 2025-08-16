package com.task.chat.domain.implement;

import org.springframework.stereotype.Service;

import com.task.chat.domain.entity.ChatQuestionDto;
import com.task.chat.domain.service.ChatClient;

@Service
public class TestChatClient implements ChatClient {

	@Override
	public String sendQuestion(ChatQuestionDto question) {
		return "";
	}
}
