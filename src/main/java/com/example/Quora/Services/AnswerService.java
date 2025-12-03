package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.AnswerDto;
import com.example.Quora.Entities.Answer;
import com.example.Quora.Entities.Question;
import com.example.Quora.Exceptions.AnswerNotFoundException;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Exceptions.UnauthorizedException;
import com.example.Quora.Repositories.AnswerRepository;
import com.example.Quora.Repositories.QuestionRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private UserService userService;

	public Answer createNewAnswer(final AnswerDto ans) throws InvalidInputException {
		if (ans == null || StringUtils.isBlank(ans.getAnswer())) {
			throw new InvalidInputException("Invalid Answer content.");
		}

		final Question question = questionService.getQuestionByQuestionId(ans.getQId());

		final Answer answer = Answer.builder().answer(ans.getAnswer()).createdBy(ans.getCreatedBy())
				.modifiedBy(ans.getModifiedBy()).question(question).build();

		final Answer newAnswer = answerRepository.save(answer);

		return newAnswer;
	}

	public Answer getAnswerByAnswerId(final int ansId) throws InvalidInputException, AnswerNotFoundException {
		if (ansId <= 0) {
			throw new InvalidInputException("Invalid Answer Id.");
		}

		final Answer ans = answerRepository.findById(ansId)
				.orElseThrow(() -> new AnswerNotFoundException("Answer with id " + ansId + " not found."));

		return ans;
	}

	public Answer deleteAnswer(final int ansId) throws InvalidInputException, AnswerNotFoundException {

		final String loggedInUsername = userService.getLoggedInUserName();
		final boolean isAdmin = userService.isloggedInUserAdmin();

		final Answer existingAns = getAnswerByAnswerId(ansId);
		final String questionCreatedBy = questionRepository.findCreatedBy(existingAns.getQuestion().getId());

		if (!(loggedInUsername.equals(existingAns.getCreatedBy()) || loggedInUsername.equals(questionCreatedBy)
				|| isAdmin)) {
			throw new UnauthorizedException("You are not authorized to perform delete operation.");
		}
		answerRepository.deleteById(ansId);

		return existingAns;
	}
}
