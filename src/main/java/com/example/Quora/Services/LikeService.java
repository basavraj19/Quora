package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.LikeDto;
import com.example.Quora.Entities.Answer;
import com.example.Quora.Entities.Like;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Repositories.LikeRepository;
import com.example.Quora.Utils.CommonUtils;

import io.micrometer.common.util.StringUtils;

@Service
public class LikeService {

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private AnswerService answerService;

	public Like newLike(final LikeDto likeDto) throws InvalidInputException {
		if (!CommonUtils.isValidObject(likeDto) && StringUtils.isBlank(likeDto.getCreatedBy())) {
			throw new InvalidInputException("Invalid Request");
		}

		final Answer answer = answerService.getAnswerByAnswerId(likeDto.getAnsId());
		Like newLike = null;

		if (answer != null) {
			final Like like = Like.builder().answerId(likeDto.getAnsId()).build();

			newLike = likeRepository.save(like);
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
