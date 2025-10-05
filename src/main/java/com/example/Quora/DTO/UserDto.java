package com.example.Quora.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Quora.Entities.Question;
import com.example.Quora.Entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDto {

	private int userId;

	private String firstName;

	private String lastName;

	private String email;

	private List<Question> questions;

	public UserDto convertToUserDto(final User user) {
		UserDto dto = new UserDto();
		dto.userId = user.getId();
		dto.firstName = user.getFirstName();
		dto.lastName = user.getLastName();
		dto.email = user.getUserName();
		dto.questions = user.getQuestions();
		return dto;
	}
}
