package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.Entities.Question;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Exceptions.QuestionNotFoundException;
import com.example.Quora.Repositories.QuestionRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	public Question createNewQuestion(final Question question) throws InvalidInputException {
		if (question.getQuestion() == null || StringUtils.isBlank(question.getQuestion())) {
			throw new InvalidInputException("Invalid Question content.");
		}

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

	public Question updateQuestion(final int qId, final String question) throws QuestionNotFoundException, InvalidInputException {

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
