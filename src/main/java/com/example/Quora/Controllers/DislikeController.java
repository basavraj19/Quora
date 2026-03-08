package com.example.Quora.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Quora.DTO.LikeDislikeRequestDto;
import com.example.Quora.Entities.Dislike;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Services.DislikeService;
import com.example.Quora.Utils.CommonUtils;
import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dislike")
@Tag(name = "6. Dislike APIs", description = "Operation related to dislikes")
public class DislikeController {

	@Autowired
	private DislikeService dislikeService;

	@PostMapping("/create")
	@Operation(summary = "Create DisLike Entry", description = "Creates a dislike for the specified answer.")
	public JsonResponseEntity<Dislike> createDisLike(@RequestBody final LikeDislikeRequestDto dislikeDto)
			throws InvalidInputException {
		final Dislike dislike = dislikeService.newDisLike(dislikeDto);

		final JsonResponseEntity<Dislike> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(dislike)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.DisLikeEntryCreatedMessage);
			response.setResult(dislike);
			response.setException(null);
			response.setStatusCode(HttpStatus.CREATED);
		}

		return response;
	}

	@GetMapping("/getDislikes/{answerId}")
	@Operation(summary = "Get DisLike Count", description = "Retrieves the total number of dislikes for the specified answer ID")
	public JsonResponseEntity<Integer> getDisLikeCount(@PathVariable final int answerId) throws InvalidInputException {
		final int dislikes = dislikeService.getDisLikeCountByAnswerId(answerId);

		final JsonResponseEntity<Integer> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.DisLikedetailsFetchedMessage);
		response.setResult(dislikes);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return response;
	}
}
