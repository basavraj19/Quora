package com.example.Quora.DTO;

import org.springframework.stereotype.Component;

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

	public UserDto convertToUserDto(final User user) {
		UserDto dto = new UserDto();
		dto.userId = user.getId();
		dto.firstName = user.getFirstName();
		dto.lastName = user.getLastName();
		dto.email = user.getUserName();

		return dto;
	}
}
