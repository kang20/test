package com.task.chat.domain.entity;

import java.util.List;

public record ChatQuestionDto(
	String question,
	List<Question> historyQuestions
) {
	public record Question(
		String userRole,
		String question
	) {
		public static Question of(String userRole, String question) {
			return new Question(userRole, question);
		}
	}
}
