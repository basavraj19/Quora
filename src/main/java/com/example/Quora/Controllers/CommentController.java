package com.example.Quora.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Quora.Entities.Comment;
import com.example.Quora.Exceptions.CommentNotFoundException;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Services.CommentService;
import com.example.Quora.Utils.CommonUtils;
import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringConstants;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/create")
	public JsonResponseEntity<Comment> createNewComment(@RequestBody final Comment comment)
			throws InvalidInputException {
		final Comment newComment = commentService.createComment(comment);

		final JsonResponseEntity<Comment> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(newComment)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.commentCreatedMessage);
			response.setResult(newComment);
			response.setException(null);
			response.setStatusCode(HttpStatus.CREATED);
		}

		return response;
	}

	@GetMapping("/fetch/{commentId}")
	public JsonResponseEntity<Comment> getComment(@PathVariable final int commentId)
			throws InvalidInputException, CommentNotFoundException {
		final Comment comment = commentService.getCommentByCommentId(commentId);

		final JsonResponseEntity<Comment> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(comment)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.commentFetchedMessage);
			response.setResult(comment);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		}

		return response;
	}

	@DeleteMapping("/delete/{commentId}")
	public JsonResponseEntity<Comment> deleteComment(@PathVariable final int commentId)
			throws InvalidInputException, CommentNotFoundException {
		final Comment deletedComment = commentService.deleteComment(commentId);

		final JsonResponseEntity<Comment> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(deletedComment)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.commentDeletedMessage);
			response.setResult(deletedComment);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		}

		return response;
	}
}
