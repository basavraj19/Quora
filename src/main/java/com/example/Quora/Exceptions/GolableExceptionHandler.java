package com.example.Quora.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Quora.DTO.UserDto;
import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringUtils;

@RestControllerAdvice
public class GolableExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public JsonResponseEntity<UserDto> throwException(final UserNotFoundException exception) {
		final JsonResponseEntity<UserDto> response = new JsonResponseEntity<>();

		response.setStatus(StringUtils.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.NOT_FOUND);

		return response;
	}
}
