package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.LikeDislikeRequestDto;
import com.example.Quora.Entities.Answer;
import com.example.Quora.Entities.Like;
import com.example.Quora.Exceptions.DuplicateEntryException;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Repositories.LikeRepository;
import com.example.Quora.Utils.CommonUtils;

@Service
public class LikeService {

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private UserService userService;

	public Like newLike(final LikeDislikeRequestDto likeDto) throws InvalidInputException, DuplicateEntryException {

		if (!CommonUtils.isValidObject(likeDto)) {
			throw new InvalidInputException("Invalid Request");
		}

		final Answer answer = answerService.getAnswerByAnswerId(likeDto.getAnsId());
		Like newLike = null;

		if (answer != null) {
			final String loggedInUser = userService.getLoggedInUserName();

			final Like existingLikeEntry = likeRepository.isUserLikedAlready(likeDto.getAnsId(), loggedInUser);

			if (existingLikeEntry == null) {
				final Like like = Like.builder().answerId(likeDto.getAnsId()).build();

				newLike = likeRepository.save(like);
			} else {
				throw new DuplicateEntryException("User already liked this answer.");
			}
		}

		return newLike;
	}

	public int getLikeCountByAnswerId(final int ansId) throws InvalidInputException {
		if (ansId <= 0) {
			throw new InvalidInputException("Invalid Answer ID.");
		}

		final int likes = likeRepository.findLikeCountByAnswerId(ansId);

		return likes;
	}
}
