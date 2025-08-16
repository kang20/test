package com.task.user.domain.fixture;

import com.task.user.domain.dto.request.CreateUserRequest;
import com.task.user.domain.dto.request.LoginRequest;

public class UserFixture {
	public static CreateUserRequest createUserRequest() {
		return new CreateUserRequest(
			"aorl2313@naver.com",
			"password123",
			"홍길동");
	}

	public static LoginRequest loginRequest(String email, String password) {
		return new LoginRequest(email, password);
	}
}
