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

@RestController
@RequestMapping("/answer")
public class AnswerController {

	@Autowired
	private AnswerService answerService;

	@PostMapping("/create")
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
