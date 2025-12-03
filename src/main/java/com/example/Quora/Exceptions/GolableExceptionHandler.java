package com.example.Quora.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringConstants;

@RestControllerAdvice
public class GolableExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public JsonResponseEntity<?> handleUserAlreadyExistsException(final UserAlreadyExistsException exception) {
		final JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.CONFLICT);

		return response;
	}

	@ExceptionHandler(UserNotFoundException.class)
	public JsonResponseEntity<?> handleUserNotFound(final UserNotFoundException exception) {
		final JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.NOT_FOUND);

		return response;
	}

	@ExceptionHandler(QuestionNotFoundException.class)
	public JsonResponseEntity<?> handleQuestionNotFound(final QuestionNotFoundException exception) {
		final JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.NOT_FOUND);

		return response;
	}

	@ExceptionHandler(AnswerNotFoundException.class)
	public JsonResponseEntity<?> handleAnswerNotFound(final AnswerNotFoundException exception) {
		final JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.NOT_FOUND);

		return response;
	}

	@ExceptionHandler(CommentNotFoundException.class)
	public JsonResponseEntity<?> handleCommentNotFound(final CommentNotFoundException exception) {
		final JsonResponseEntity<?> response = new JsonResponseEntity<>();

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

	@ExceptionHandler(DuplicateEntryException.class)
	public JsonResponseEntity<?> handleDplicateEntryException(final DuplicateEntryException exception) {
		JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.CONFLICT);

		return response;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public JsonResponseEntity<?> handleResourceNotFoundException(final ResourceNotFoundException exception) {
		JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.NOT_FOUND);

		return response;
	}

	@ExceptionHandler(UnauthorizedException.class)
	public JsonResponseEntity<?> handleUnauthorizedException(final UnauthorizedException exception) {
		JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.UNAUTHORIZED);

		return response;
	}
}
