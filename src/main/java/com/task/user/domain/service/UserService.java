package com.task.user.domain.service;

import static org.springframework.http.HttpStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.task.auth.jwt.JwtHandler;
import com.task.global.exception.DomainExcecption;
import com.task.user.domain.dto.request.CreateUserRequest;
import com.task.user.domain.dto.request.LoginRequest;
import com.task.user.domain.entity.Email;
import com.task.user.domain.entity.PasswordEncoder;
import com.task.user.domain.entity.User;
import com.task.user.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {
	public final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtHandler jwtHandler;

	@Transactional
	public void signup(@Valid CreateUserRequest req) {
		userRepository.findByEmail(new Email(req.email()))
			.ifPresent(user -> {
				throw DomainExcecption.of(CONFLICT,"이미 존재하는 이메일입니다.");
			});

		var user = User.create(req,passwordEncoder);

		userRepository.save(user);
	}

	@Transactional
	public String login(@Valid LoginRequest req) {
		var user = userRepository.findByEmail(new Email(req.email()))
			.orElseThrow(() -> DomainExcecption.of(UNAUTHORIZED, "존재하지 않는 이메일입니다."));

		if(!passwordEncoder.matches(req.password(), user.getPassword())) {
			throw DomainExcecption.of(UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
		}

		return jwtHandler.issue(
			user.getId(),
			user.getEmail().value(),
			user.getName(),
			user.getRole().name()
		);
	}



}
