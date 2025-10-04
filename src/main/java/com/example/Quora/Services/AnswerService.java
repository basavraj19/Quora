package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.Entities.Answer;
import com.example.Quora.Exceptions.AnswerNotFoundException;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Repositories.AnswerRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository answerRepository;

	public Answer createNewAnswer(final Answer ans) throws InvalidInputException {
		if (ans == null || StringUtils.isBlank(ans.getAnswer())) {
			throw new InvalidInputException("Invalid Answer content.");
		}

		final Answer newAnswer = answerRepository.save(ans);

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
