package com.task.user.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.task.user.domain.dto.request.CreateUserRequest;

class UserTest {

	class TestPasswordEncoder implements PasswordEncoder {
		@Override
		public String encode(String password) {
			return password.toUpperCase();
		}

		@Override
		public boolean matches(String password, String encodedHash) {
			return encodedHash.equals(password.toUpperCase());
		}
	}

	@Test
	void creaeteUser(){
		CreateUserRequest req = new CreateUserRequest("aorl2313@naver.com", "1234", "홍길동");

		User user = User.create(req,new TestPasswordEncoder());
	}
}