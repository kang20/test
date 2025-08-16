package com.task.chat.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.task.global.RepositoryTest;

class ChatThreadRepositoryTest extends RepositoryTest {

	@Autowired
	ChatThreadRepository chatThreadRepository;

	@Test
	void queryTest() {
		chatThreadRepository.findTopByUserIdOrderByIdDesc(1L);
	}
}