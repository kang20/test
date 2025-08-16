package com.task.chat.domain.entity;

import com.task.global.domain.AbstractEntity;
import com.task.user.domain.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_threads")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class ChatThread extends AbstractEntity {
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;

	public static ChatThread create(User user) {
		ChatThread chatThread = new ChatThread();
		chatThread.user = user;
		return chatThread;
	}
}
