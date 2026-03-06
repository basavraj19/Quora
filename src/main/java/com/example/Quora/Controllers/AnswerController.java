package com.example.Quora.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Quora.DTO.AnswerDto;
import com.example.Quora.Entities.Answer;
import com.example.Quora.Exceptions.AnswerNotFoundException;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Services.AnswerService;
import com.example.Quora.Utils.CommonUtils;
import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/answer")
@Tag(name = "Answer APIs", description = "APIs for creating and retrieving answers")
public class AnswerController {

	@Autowired
	private AnswerService answerService;

	@PostMapping("/create")
	@Operation(summary = "Create Answer", description = "Creates a new answer associated with the specified question")
	public JsonResponseEntity<Answer> createAnswer(@RequestBody final AnswerDto ans) throws InvalidInputException {

		final Answer newAnswer = answerService.createNewAnswer(ans);

		final JsonResponseEntity<Answer> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(newAnswer)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.answerCreatedMessage);
			response.setResult(newAnswer);
			response.setException(null);
			response.setStatusCode(HttpStatus.CREATED);
		}

		return response;
	}

	@GetMapping("/search/{ansId}")
	@Operation(summary = "Get Answer", description = "Retrieves an answer based on the provided answer ID")
	public JsonResponseEntity<Answer> getAnswerByAnswerId(@PathVariable final int ansId)
			throws InvalidInputException, AnswerNotFoundException {
		final Answer answer = answerService.getAnswerByAnswerId(ansId);

		final JsonResponseEntity<Answer> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(answer)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.answerFetchedMessage);
			response.setResult(answer);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		}

		return response;
	}

	@DeleteMapping("/delete/{ansId}")
	@Operation(summary = "Delete Answer", description = "Deletes an answer based on the provided answer ID")
	public JsonResponseEntity<Answer> deleteAnswerByAnswerId(@PathVariable final int ansId)
			throws InvalidInputException, AnswerNotFoundException {
		final Answer deletedAnswer = answerService.deleteAnswer(ansId);

		final JsonResponseEntity<Answer> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(deletedAnswer)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.answerDeletedMessage);
			response.setResult(deletedAnswer);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		}

		return response;
	}
}
