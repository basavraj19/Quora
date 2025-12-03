package com.example.Quora.Services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.UserDto;
import com.example.Quora.Entities.Role;
import com.example.Quora.Entities.User;
import com.example.Quora.Exceptions.UnauthorizedException;
import com.example.Quora.Exceptions.UserAlreadyExistsException;
import com.example.Quora.Exceptions.UserNotFoundException;
import com.example.Quora.Repositories.UserRepository;
import com.example.Quora.Utils.CommonUtils;
import com.example.Quora.Utils.JWTUtils;
import com.example.Quora.Utils.RoleEnum;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDto userDto;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDto createNewUser(final User user) throws Exception {

		final User existingUser = userRepository.findByUserName(user.getUserName()).orElse(null);

		if (existingUser != null) {
			throw new UserAlreadyExistsException("User alredy present.");
		}

		if (CommonUtils.isValidObject(user)) {
			final String encodedPassword = encryptPassword(user.getPassword());
			user.setPassword(encodedPassword);
			final User newUser = userRepository.save(user);
			final UserDto dto = userDto.convertToUserDto(newUser);
			return dto;
		}

		return null;
	}

	public String login(final User user) throws UserNotFoundException {
		final User existingUser = userRepository.findByUserName(user.getUserName())
				.orElseThrow(() -> new UserNotFoundException("user " + user.getUserName() + " not found."));

		if (CommonUtils.isValidObject(existingUser)) {
			boolean isvalidUser = passwordEncoder.matches(user.getPassword(), existingUser.getPassword());

			if (isvalidUser) {
				final List<RoleEnum> roles = getUserRoles(existingUser);
				final String jwt = jwtUtils.generateToken(user.getUserName(), roles);
				return jwt;
			}
		}

		return null;
	}

	public List<RoleEnum> getUserRoles(final User user) {
		Set<Role> roles = user.getRoles();
		List<RoleEnum> userRole = roles.stream().map(role -> role.getRole()).collect(Collectors.toList());
		return userRole;
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
		final String loggedInUser = getLoggedInUserName();
		final boolean isAdmin = isloggedInUserAdmin();

		if (!(loggedInUser.equals(userName) || isAdmin)) {
			throw new UnauthorizedException("You are not authorized to perform delete operation.");
		}
		
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
		final String loggedInUser = getLoggedInUserName();

		if (!loggedInUser.equals(userName)) {
			throw new UnauthorizedException("You are not authorized to perform update operation.");
		}
		
		final User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new UserNotFoundException("User not found."));

		if (CommonUtils.isValidObject(user)) {
			final String encodedPassword = encryptPassword(password);
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
	
	public String getLoggedInUserName() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	public boolean isloggedInUserAdmin() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
	}
}
