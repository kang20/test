package com.task.user.domain.entity;

import static jakarta.persistence.EnumType.*;
import static java.util.Objects.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import com.task.global.domain.AbstractEntity;
import com.task.user.domain.dto.request.CreateUserRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class User extends AbstractEntity {
	@Embedded
	private Email email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Enumerated(value = STRING)
	private UserRole role;

	public static User create(CreateUserRequest request, PasswordEncoder passwordEncoder) {
		User user = new User();
		user.email = new Email(request.email());
		user.password = requireNonNull(passwordEncoder.encode(request.password()));
		user.name = requireNonNull(request.name());
		user.createdAt = LocalDateTime.now();
		user.role = UserRole.MEMBER;
		return user;
	}
}
