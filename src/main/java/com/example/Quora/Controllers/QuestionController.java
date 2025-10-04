package com.example.Quora.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Quora.Entities.Question;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Exceptions.QuestionNotFoundException;
import com.example.Quora.Services.QuestionService;
import com.example.Quora.Utils.CommonUtils;
import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringConstants;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@PostMapping("/create")
	public JsonResponseEntity<Question> createNewQuestion(@RequestBody final Question question)
			throws InvalidInputException {
		final Question newQuestion = questionService.createNewQuestion(question);

		final JsonResponseEntity<Question> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(newQuestion)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.questionCreatedMessage);
			response.setResult(newQuestion);
			response.setException(null);
			response.setStatusCode(HttpStatus.CREATED);
		}
		return response;
	}

	@GetMapping("/search/{qId}")
	public JsonResponseEntity<Question> getQuestionById(@PathVariable final int qId)
			throws InvalidInputException, QuestionNotFoundException {
		final Question question = questionService.getQuestionByQuestionId(qId);

		final JsonResponseEntity<Question> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(response)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.questionFetchedMessage);
			response.setResult(question);
			response.setStatusCode(HttpStatus.OK);
			response.setException(null);
		}
		return response;
	}

	@PutMapping("/updateQuestion")
	public JsonResponseEntity<Question> updateQuestion(@RequestBody final Question question)
			throws InvalidInputException, QuestionNotFoundException {
		final Question updatedQuestion = questionService.updateQuestion(question.getId(), question.getQuestion());

		final JsonResponseEntity<Question> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(response)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.questionUpdatedMessage);
			response.setResult(updatedQuestion);
			response.setStatusCode(HttpStatus.OK);
			response.setException(null);
		}
		return response;
	}

	@DeleteMapping("/delete/{qId}")
	public JsonResponseEntity<Question> deleteQuestion(@PathVariable final int qId)
			throws InvalidInputException, QuestionNotFoundException {
		final Question deletedQuestion = questionService.deleteQuestionByQuestionId(qId);

		final JsonResponseEntity<Question> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(response)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.questionDeletedMessage);
			response.setResult(deletedQuestion);
			response.setStatusCode(HttpStatus.OK);
			response.setException(null);
		}
		return response;
	}
}
