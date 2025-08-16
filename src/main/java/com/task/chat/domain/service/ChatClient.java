package com.task.chat.domain.service;

import com.task.chat.domain.entity.ChatQuestionDto;

public interface ChatClient {
	String sendQuestion(ChatQuestionDto question);
}
