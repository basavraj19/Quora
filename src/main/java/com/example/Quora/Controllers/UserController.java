package com.example.Quora.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Quora.DTO.UserDto;
import com.example.Quora.Entities.User;
import com.example.Quora.Exceptions.UserNotFoundException;
import com.example.Quora.Services.UserService;
import com.example.Quora.Utils.CommonUtils;
import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringUtils;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signUp")
	public JsonResponseEntity<UserDto> createNewUser(@RequestBody final User user) throws Exception {
		final UserDto newUser = userService.createNewUser(user);

		final JsonResponseEntity<UserDto> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(newUser)) {
			response.setStatus(StringUtils.success);
			response.setMessage(StringUtils.userCreatedMessage);
			response.setResult(newUser);
			response.setException(null);
			response.setStatusCode(HttpStatusCode.valueOf(200));
		} else {
			response.setStatus(StringUtils.failed);
			response.setMessage(StringUtils.failedUserCreatedMessage);
			response.setResult(null);
			response.setException(new Exception("Internal Server Error"));
			response.setStatusCode(HttpStatus.CREATED);
		}

		return response;
	}

	@GetMapping("/search")
	public JsonResponseEntity<UserDto> findUser(@RequestParam final String userName) throws UserNotFoundException {
		final UserDto user = userService.getUserByUserName(userName);

		final JsonResponseEntity<UserDto> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(user)) {
			response.setStatus(StringUtils.success);
			response.setMessage(StringUtils.userFetchedMessage);
			response.setResult(user);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		}
		return response;
	}

	@DeleteMapping("/delete")
	public JsonResponseEntity<UserDto> deleteUser(@RequestParam final String userName) throws UserNotFoundException {
		final UserDto user = userService.deleteUser(userName);

		final JsonResponseEntity<UserDto> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(user)) {
			response.setStatus(StringUtils.success);
			response.setMessage(StringUtils.userDeletedMessage);
			response.setResult(user);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		}

		return response;
	}

	@PatchMapping("/update")
	public JsonResponseEntity<UserDto> updatePassword(@RequestBody User user) throws UserNotFoundException {
		final UserDto updatedUser = userService.updatePassword(user.getUserName(), user.getPassword());

		final JsonResponseEntity<UserDto> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(user)) {
			response.setStatus(StringUtils.success);
			response.setMessage(StringUtils.passwordUpdatedMessage);
			response.setResult(updatedUser);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		}

		return response;
	}
}
