package com.example.Quora.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Quora.DTO.UserDto;
import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringConstants;

@RestControllerAdvice
public class GolableExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public JsonResponseEntity<UserDto> handleUserNotFound(final UserNotFoundException exception) {
		final JsonResponseEntity<UserDto> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.NOT_FOUND);

		return response;
	}

	@ExceptionHandler(QuestionNotFoundException.class)
	public JsonResponseEntity<UserDto> handleQuestionNotFound(final QuestionNotFoundException exception) {
		final JsonResponseEntity<UserDto> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.NOT_FOUND);

		return response;
	}

	@ExceptionHandler(InvalidInputException.class)
	public JsonResponseEntity<?> handleInvalidInput(final InvalidInputException exception) {
		final JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.BAD_REQUEST);

		return response;
	}
}
