package com.example.Quora.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Quora.Entities.Like;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Services.LikeService;
import com.example.Quora.Utils.CommonUtils;
import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringConstants;

@RestController
@RequestMapping("/like")
public class LikeController {

	@Autowired
	private LikeService likeService;

	@PostMapping("/create")
	public JsonResponseEntity<Like> createLike(@RequestBody final Like likeObj) throws InvalidInputException {
		final Like like = likeService.newLike(likeObj);

		final JsonResponseEntity<Like> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(like)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.commentDeletedMessage);
			response.setResult(like);
			response.setException(null);
			response.setStatusCode(HttpStatus.CREATED);
		}

		return response;
	}

	@GetMapping("/getLikes/{answerId}")
	public JsonResponseEntity<Long> getLikeCount(@PathVariable final int answerId) throws InvalidInputException {
		final Long likes = likeService.getLikeCountByAnswerId(answerId);

		final JsonResponseEntity<Long> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.commentDeletedMessage);
		response.setResult(likes);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return response;
	}
}
