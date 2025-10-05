package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.QuestionDto;
import com.example.Quora.DTO.UserDto;
import com.example.Quora.Entities.Question;
import com.example.Quora.Entities.User;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Exceptions.QuestionNotFoundException;
import com.example.Quora.Repositories.QuestionRepository;

import io.micrometer.common.util.StringUtils;

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
		final UserDto userDto = userService.getUserByUserName(questionDto.getUserName());

		final User user = User.builder().id(userDto.getUserId()).userName(userDto.getEmail())
				.firstName(userDto.getFirstName()).lastName(userDto.getLastName()).build();

		final Question question = Question.builder().question(questionDto.getQuestion())
				.createdBy(questionDto.getCreatedBy()).modifiedBy(questionDto.getModifiedBy()).user(user).build();

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

	public Question updateQuestion(final int qId, final String question)
			throws QuestionNotFoundException, InvalidInputException {

		if (question == null || StringUtils.isBlank(question)) {
			throw new InvalidInputException("Invalid Question content.");
		}

		Question existingQuestion = getQuestionByQuestionId(qId);

		existingQuestion.setQuestion(question);
		final Question updatedQuestion = questionRepository.save(existingQuestion);

		return updatedQuestion;
	}

	public Question deleteQuestionByQuestionId(final int qId) throws QuestionNotFoundException, InvalidInputException {
		Question existingQuestion = getQuestionByQuestionId(qId);

		questionRepository.deleteById(qId);

		return existingQuestion;
	}

}
