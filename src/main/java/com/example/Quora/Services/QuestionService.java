package com.example.Quora.Services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.QuestionDto;
import com.example.Quora.Entities.Question;
import com.example.Quora.Entities.User;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Exceptions.QuestionNotFoundException;
import com.example.Quora.Exceptions.UnauthorizedException;
import com.example.Quora.Exceptions.UserNotFoundException;
import com.example.Quora.Repositories.QuestionRepository;
import com.example.Quora.Repositories.UserRepository;
import com.example.Quora.Utils.CommonUtils;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	public Question createNewQuestion(final QuestionDto questionDto) throws InvalidInputException {
		if (questionDto.getQuestion() == null || StringUtils.isBlank(questionDto.getQuestion())) {
			throw new InvalidInputException("Invalid Question content.");
		}
		final String loggedInUser = userService.getLoggedInUserName();

		final User user = userRepository.findByUserName(loggedInUser)
				.orElseThrow(() -> new UserNotFoundException("User " + loggedInUser + " not found."));

		final Question question = Question.builder().question(questionDto.getQuestion()).user(user).build();

		Question newQuestion = questionRepository.save(question);

		return newQuestion;
	}

	public List<Question> search(final String question) throws QuestionNotFoundException, InvalidInputException {
		if (question == null || question.trim().isEmpty()) {
			throw new InvalidInputException("Search query cannot be empty.");
		}

		String[] words = question.toLowerCase().split("\\s+");

		long validWordCount = Arrays.stream(words)
				.filter(word -> !CommonUtils.STOP_WORDS.contains(word) && word.length() >= 3).count();

		if (validWordCount == 0) {
			throw new InvalidInputException("Please enter meaningful search keywords.");
		}

		final List<Question> questions = questionRepository.getQuestionByContent(question);

		if (questions.isEmpty()) {
			throw new QuestionNotFoundException("No relevant data found.");
		}

		return questions;
	}

	public Question getQuestionByQuestionId(final int qId) throws QuestionNotFoundException, InvalidInputException {
		if (qId <= 0) {
			throw new InvalidInputException("Invalid Question Id.");
		}

		return questionRepository.findById(qId)
				.orElseThrow(() -> new QuestionNotFoundException("Question with id " + qId + " not found."));
	}

	public Question updateQuestion(final int qId, final QuestionDto question)
			throws QuestionNotFoundException, InvalidInputException {
		final String loggedInUsername = userService.getLoggedInUserName();

		if (question == null || StringUtils.isBlank(question.getQuestion())) {
			throw new InvalidInputException("Invalid Question content.");
		}

		Question existingQuestion = getQuestionByQuestionId(qId);

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
