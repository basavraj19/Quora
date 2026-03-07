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

	private String firstName;

	private String lastName;

	private String userName;

	private String password;
	
	private int roleId;

	public UserDto convertToUserDto(User existingUser) {
		// TODO Auto-generated method stub
		UserDto user = new UserDto();
		user.setUserName(existingUser.getUserName());
		user.setFirstName(existingUser.getFirstName());
		user.setLastName(existingUser.getLastName());
		return user;
	}
}
