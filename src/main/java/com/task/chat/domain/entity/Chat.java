package com.task.chat.domain.entity;

import java.time.LocalDateTime;

import com.task.global.domain.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chats")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Chat extends AbstractEntity {
	@Lob
	@Column(nullable = false)
	private String question;

	@Lob
	@Column(nullable = false)
	private String answer;

	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "chat_thread_id", nullable = false)
	private ChatThread chatThread;

	public static Chat create(final String question, final String answer, final ChatThread chatThread) {
		var chat = new Chat();
		chat.question = question;
		chat.answer = answer;
		chat.createdAt = LocalDateTime.now();
		chat.chatThread = chatThread;

		return chat;
	}
}
