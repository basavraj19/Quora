package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.UserDto;
import com.example.Quora.Entities.User;
import com.example.Quora.Exceptions.UserNotFoundException;
import com.example.Quora.Repositories.UserRepository;
import com.example.Quora.Utils.CommonUtils;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDto userDto;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDto createNewUser(final User user) throws Exception {

		if (CommonUtils.isValidObject(user)) {
			final String encodedPassword = encryptPassword(user.getPassword());
			user.setPassword(encodedPassword);
			final User newUser = userRepository.save(user);
			final UserDto dto = userDto.convertToUserDto(newUser);
			return dto;
		}

		return null;
	}
	
	public UserDto login(final User user) throws UserNotFoundException {
		final User existingUser = userRepository.findByUserName(user.getUserName()).orElseThrow(
				() -> new UserNotFoundException("user "+user.getUserName()+" not found."));
		
		if(CommonUtils.isValidObject(existingUser)) {
			boolean isvalidUser = passwordEncoder.matches(user.getPassword(), existingUser.getPassword());
			
			if(isvalidUser) {
				final UserDto dto = userDto.convertToUserDto(existingUser);

				return dto;
			}
		}
		
		return null;
	}

	public UserDto getUserByUserName(final String userName) throws UserNotFoundException {
		final User existingUser = userRepository.findByUserName(userName)
				.orElseThrow(() -> new UserNotFoundException("User " + userName + " not found."));

		if (CommonUtils.isValidObject(existingUser)) {
			final UserDto dto = userDto.convertToUserDto(existingUser);

			return dto;
		}

		return null;
	}

	public UserDto deleteUser(final String userName) throws UserNotFoundException {
		final User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new UserNotFoundException("User not found."));

		if (CommonUtils.isValidObject(user)) {
			userRepository.deleteById(user.getId());
			final UserDto dto = userDto.convertToUserDto(user);

			return dto;
		}

		return null;
	}

	public UserDto updatePassword(final String userName, final String password) throws UserNotFoundException {
		final User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new UserNotFoundException("User not found."));

		if (CommonUtils.isValidObject(user)) {
			final String encodedPassword = encryptPassword(user.getPassword());
			user.setPassword(encodedPassword);
			userRepository.save(user);
			final UserDto dto = userDto.convertToUserDto(user);

			return dto;
		}

		return null;
	}

	public String encryptPassword(final String password) {
		return passwordEncoder.encode(password);
	}
}
