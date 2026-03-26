package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.LikeDislikeRequestDto;
import com.example.Quora.Entities.Answer;
import com.example.Quora.Entities.Dislike;
import com.example.Quora.Exceptions.DuplicateEntryException;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Repositories.DislikeRepository;
import com.example.Quora.Utils.CommonUtils;

@Service
public class DislikeService {

	@Autowired
	private DislikeRepository dislikeRepository;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private UserService userService;

	public Dislike newDisLike(final LikeDislikeRequestDto dislikeDto)
			throws InvalidInputException, DuplicateEntryException {

		if (!CommonUtils.isValidObject(dislikeDto)) {
			throw new InvalidInputException("Invalid Request");
		}

		final Answer answer = answerService.getAnswerByAnswerId(dislikeDto.getAnsId());
		Dislike newEntry = null;

		if (answer != null) {
			final String loggedInUser = userService.getLoggedInUserName();
			final Dislike existingDislikeEntry = dislikeRepository.isUserDisliked(dislikeDto.getAnsId(), loggedInUser);

			if (existingDislikeEntry == null) {
				final Dislike dislike = Dislike.builder().answerId(dislikeDto.getAnsId()).build();

				newEntry = dislikeRepository.save(dislike);

			} else {
				throw new DuplicateEntryException("User already disliked this answer.");
			}

		}

		return newEntry;
	}

	public int getDisLikeCountByAnswerId(final int ansId) throws InvalidInputException {
		if (ansId <= 0) {
			throw new InvalidInputException("Invalid Answer ID.");
		}

		final int dislikes = dislikeRepository.findDisLikeCountByAnswerId(ansId);

		return dislikes;
	}
}
