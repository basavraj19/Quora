package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.Entities.Like;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Repositories.LikeRepository;
import com.example.Quora.Utils.CommonUtils;

import io.micrometer.common.util.StringUtils;

@Service
public class LikeService {

	@Autowired
	private LikeRepository likeRepository;

	public Like newLike(final Like like) throws InvalidInputException {
		if (!CommonUtils.isValidObject(like) && StringUtils.isBlank(like.getCreatedBy())) {
			throw new InvalidInputException("Invalid Request");
		}

		final Like newLike = likeRepository.save(like);

		return newLike;
	}

	public Long getLikeCountByAnswerId(final int ansId) throws InvalidInputException {
		if (ansId <= 0) {
			throw new InvalidInputException("Invalid Answer ID.");
		}

		final Long likes = likeRepository.findLikeCountByAnswerId(ansId);

		return likes;
	}
}
