package com.task.user.domain.service;

import static com.task.user.domain.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.task.global.ServiceTest;
import com.task.global.exception.DomainExcecption;
import com.task.user.domain.entity.Email;
import com.task.user.domain.fixture.UserFixture;
import com.task.user.repository.UserRepository;

class UserServiceTest extends ServiceTest {
	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Test
	void signup() {
		var req = createUserRequest();

		userService.signup(req);

		assertThat(userRepository.findByEmail(new Email(req.email())).get())
			.isNotNull();
	}

	@Test
	void loginTest() {
		var req = createUserRequest();
		userService.signup(req); // 회원가입 먼저 수행

		var loginReq = loginRequest(req.email(), req.password());

		String token = userService.login(loginReq);

		assertThat(token).isNotBlank();
	}

	@Test
	void loginFailTest() {
		var loginReq = loginRequest("not_exist@mail.com", "anyPassword");

		assertThatThrownBy(() -> userService.login(loginReq))
			.isInstanceOf(DomainExcecption.class);
	}

}