package com.task.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.task.chat.domain.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
	Optional<Chat> findTopByChatThreadIdOrderByCreatedAtDesc(Long threadId);

	List<Chat> findByChatThreadId(Long threadId);

	@Query(
		"DELETE FROM Chat c WHERE c.chatThread.id = :threadId"
	)
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional
	void deleteByChatThreadId(Long threadId);
}
