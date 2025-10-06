package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.AnswerDto;
import com.example.Quora.Entities.Answer;
import com.example.Quora.Entities.Question;
import com.example.Quora.Exceptions.AnswerNotFoundException;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Repositories.AnswerRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionService questionService;

	public Answer createNewAnswer(final AnswerDto ans) throws InvalidInputException {
		if (ans == null || StringUtils.isBlank(ans.getAnswer())) {
			throw new InvalidInputException("Invalid Answer content.");
		}

		final Question question = questionService.getQuestionByQuestionId(ans.getQId());

		final Answer answer = Answer.builder().answer(ans.getAnswer()).createdBy(ans.getCreatedBy())
				.modifiedBy(ans.getModofiedBy()).question(question).build();

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

		final Answer existingAns = getAnswerByAnswerId(ansId);

		answerRepository.deleteById(ansId);

		return existingAns;
	}
}
