package com.example.Quora.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.QuestionDto;
import com.example.Quora.DTO.UserDto;
import com.example.Quora.Entities.Question;
import com.example.Quora.Entities.User;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Exceptions.QuestionNotFoundException;
import com.example.Quora.Exceptions.UnauthorizedException;
import com.example.Quora.Repositories.QuestionRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserService userService;

	public Question createNewQuestion(final QuestionDto questionDto) throws InvalidInputException {
		if (questionDto.getQuestion() == null || StringUtils.isBlank(questionDto.getQuestion())) {
			throw new InvalidInputException("Invalid Question content.");
		}
		final String loggedInUser = userService.getLoggedInUserName();
		final UserDto userDto = userService.getUserByUserName(loggedInUser);

		final User user = User.builder().id(userDto.getUserId()).userName(userDto.getEmail())
				.firstName(userDto.getFirstName()).lastName(userDto.getLastName()).build();

		final Question question = Question.builder().question(questionDto.getQuestion()).user(user).build();

		Question newQuestion = questionRepository.save(question);

		return newQuestion;
	}

	public Question getQuestionByQuestionId(final int qId) throws QuestionNotFoundException, InvalidInputException {
		if (qId <= 0) {
			throw new InvalidInputException("Invalid Question Id.");
		}

		return questionRepository.findById(qId)
				.orElseThrow(() -> new QuestionNotFoundException("Question with id " + qId + " not found."));
	}

	public Question updateQuestion(final Question question) throws QuestionNotFoundException, InvalidInputException {
		final String loggedInUsername = userService.getLoggedInUserName();

		if (question == null || StringUtils.isBlank(question.getQuestion())) {
			throw new InvalidInputException("Invalid Question content.");
		}

		Question existingQuestion = getQuestionByQuestionId(question.getId());

		if (!loggedInUsername.equals(existingQuestion.getCreatedBy())) {
			throw new UnauthorizedException("You are not authorized to perform this operation.");
		}

		existingQuestion.setQuestion(question.getQuestion());

		final Question updatedQuestion = questionRepository.save(existingQuestion);

		return updatedQuestion;
	}

	@Transactional
	public Question deleteQuestionByQuestionId(final int qId) throws QuestionNotFoundException, InvalidInputException {
		final String loggedInUsername = userService.getLoggedInUserName();
		final boolean isAdmin = userService.isloggedInUserAdmin();

		Question existingQuestion = getQuestionByQuestionId(qId);

		if (!(loggedInUsername.equals(existingQuestion.getCreatedBy()) || isAdmin)) {
			throw new UnauthorizedException("You are not authorized to perform delete operation.");
		}

		try {
			questionRepository.deleteById(qId);
		} catch (Exception e) {
			throw e;
		}

		return existingQuestion;
	}

}
