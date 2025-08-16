package com.task.chat.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.chat.domain.entity.Chat;
import com.task.chat.domain.entity.ChatQuestionDto;
import com.task.chat.domain.entity.ChatThread;
import com.task.chat.repository.ChatRepository;
import com.task.chat.repository.ChatThreadRepository;
import com.task.global.exception.DomainExcecption;
import com.task.user.domain.entity.User;
import com.task.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
	private static final long THREAD_WINDOW_MIN = 30L;
	private final ChatRepository chatRepository;
	private final ChatThreadRepository chatThreadRepository;
	private final UserRepository userRepository;
	private final ChatClient chatClient;

	public String createChat(final Long userId, final String question) {
		User user = userRepository.findById(userId).orElseThrow(() -> {
			return DomainExcecption.of(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");
		});
		var now = LocalDateTime.now();
		var windowStart = now.minusMinutes(THREAD_WINDOW_MIN);

		// 30분 이내에 대화가 있다면 해당 스레드 단위로 묶고 없다면 스레드를 새로 생성 기존 스레드 단위로 묶는다면 스레드의 채팅 내역들을 다 가지고 와야함
		ChatThread threadToUse = chatThreadRepository
			.findTopByUserIdOrderByIdDesc(userId)
			.filter(t -> isRecentThread(t.getId(), windowStart)) // 30분 이내 대화가 있으면 재사용
			.orElseGet(() -> chatThreadRepository.save(ChatThread.create(user))); // 없으면 새로 생

		List<ChatQuestionDto.Question> historyChats = chatRepository.findByChatThreadId(threadToUse.getId())
			.stream()
			.map(chat->{
				return ChatQuestionDto.Question.of(user.getRole().name(), chat.getQuestion());})
			.toList();

		String response = chatClient.sendQuestion(new ChatQuestionDto(question, historyChats)); // TODO : 일단 동기 처리 추후 비동기로 개선

		// 요청받은 응답을 반환하고 질문과 Chat 을 DB에 저장
		var chat = Chat.create(question, response, threadToUse);
		chatRepository.save(chat);

		return response;
	}

	@Transactional
	public void deleteChatThread(final Long userId,final Long threadId) {
		ChatThread thread = chatThreadRepository.findById(threadId)
			.orElseThrow(() -> DomainExcecption.of(HttpStatus.NOT_FOUND, "존재하지 않는 채팅 스레드입니다."));

		if( !thread.getUser().getId().equals(userId)) {
			throw DomainExcecption.of(HttpStatus.FORBIDDEN, "해당 스레드는 사용자의 것이 아닙니다.");
		}

		chatRepository.deleteByChatThreadId(thread.getId());

		chatThreadRepository.delete(thread);
	}

	private boolean isRecentThread(Long threadId, LocalDateTime windowStart) {
		return chatRepository.findTopByChatThreadIdOrderByCreatedAtDesc(threadId)
			.map(last -> !last.getCreatedAt().isBefore(windowStart)) // windowStart 이후면 true
			.orElse(false); // 채팅이 하나도 없으면 최근으로 보지 않음
	}
}
