package com.task.user.Controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.task.user.domain.dto.request.CreateUserRequest;
import com.task.user.domain.dto.request.LoginRequest;
import com.task.user.domain.dto.response.TokenResponse;
import com.task.user.domain.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/auth/signup")
	public void signup(@Valid @RequestBody CreateUserRequest req){
		userService.signup(req);
	}

	@PostMapping("/auth/login")
	public TokenResponse login(@Valid @RequestBody LoginRequest req){
		String token = userService.login(req);
		return new TokenResponse(token);
	}
}