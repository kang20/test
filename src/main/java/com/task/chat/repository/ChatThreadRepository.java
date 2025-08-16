package com.task.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.task.chat.domain.entity.ChatThread;

@Repository
public interface ChatThreadRepository extends JpaRepository<ChatThread, Long> {
	Optional<ChatThread> findTopByUserIdOrderByIdDesc(Long userId);
}
