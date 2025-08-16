package com.task.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.chat.domain.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
	Optional<Chat> findTopByChatThreadIdOrderByCreatedAtDesc(Long threadId);

	List<Chat> findByChatThreadId(Long threadId);
}
