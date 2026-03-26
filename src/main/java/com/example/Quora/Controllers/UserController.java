package com.example.Quora.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Quora.DTO.UserDto;
import com.example.Quora.DTO.UserLoginRequestDto;
import com.example.Quora.Exceptions.UserNotFoundException;
import com.example.Quora.Services.UserService;
import com.example.Quora.Utils.CommonUtils;
import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "1. User APIs", description = "User management APIs")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signUp")
	@Operation(summary = "Create User", description = "Creates a new user in the system")
	@SecurityRequirements()
	public JsonResponseEntity<String> createNewUser(@RequestBody final UserDto user) throws Exception {
		final String newUserName = userService.createNewUser(user);

		final JsonResponseEntity<String> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(newUserName)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.userCreatedMessage);
			response.setResult(newUserName);
			response.setException(null);
			response.setStatusCode(HttpStatus.CREATED);
		} else {
			response.setStatus(StringConstants.failed);
			response.setMessage(StringConstants.failedUserCreatedMessage);
			response.setResult(null);
			response.setException(new Exception("Internal Server Error"));
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@PostMapping("/login")
	@Operation(summary = "User Login", description = "Validates user credentials and generates a JWT token")
	@SecurityRequirements()
	public ResponseEntity<JsonResponseEntity<String>> login(@RequestBody final UserLoginRequestDto user)
			throws UserNotFoundException {
		final String jwt = userService.login(user);

		final JsonResponseEntity<String> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidString(jwt)) {

			final long expiryTime = 5 * 60;

			final ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", jwt).httpOnly(true).secure(false).path("/")
					.sameSite("Lax").maxAge(expiryTime).build();

			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.userLoggedMessage);
			response.setResult(null);
			response.setException(null);
			response.setStatusCode(HttpStatus.ACCEPTED);

			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);

		} else {
			response.setStatus(StringConstants.failed);
			response.setMessage(StringConstants.inValidPasswordMessage);
			response.setResult(null);
			response.setException(null);
			response.setStatusCode(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.badRequest().body(response);
	}

	@GetMapping("/search")
	@Operation(summary = "Search User", description = "Retrieves user based on the provided username")
	public JsonResponseEntity<UserDto> findUser(@RequestParam final String Username) throws UserNotFoundException {
		final UserDto user = userService.getUserByUserName(Username);

		final JsonResponseEntity<UserDto> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(user)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.userFetchedMessage);
			response.setResult(user);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		}
		return response;
	}

	@DeleteMapping("/delete")
	@Operation(summary = "Delete User", description = "Deletes a user based on the provided username")
	public JsonResponseEntity<UserDto> deleteUser(@RequestParam final String Username) throws UserNotFoundException {
		final UserDto user = userService.deleteUser(Username);

		final JsonResponseEntity<UserDto> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(user)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.userDeletedMessage);
			response.setResult(user);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		}

		return response;
	}

	@PatchMapping("/update")
	@Operation(summary = "Update User Password", description = "Updates the password for the specified user")
	public JsonResponseEntity<UserDto> updatePassword(@RequestBody UserLoginRequestDto user)
			throws UserNotFoundException {
		final UserDto updatedUser = userService.updatePassword(user.getUserName(), user.getPassword());

		final JsonResponseEntity<UserDto> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(user)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.passwordUpdatedMessage);
			response.setResult(updatedUser);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		}

		return response;
	}
}
