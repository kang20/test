package com.task.user.domain.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailTest {

	@Test
	void newEmailFailTest() {
		var emailValue = "asdfsfd";

		assertThatThrownBy(() ->
			new Email(emailValue)
		).isInstanceOf(IllegalArgumentException.class);
	}

}