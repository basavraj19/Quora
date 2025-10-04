package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.Entities.Comment;
import com.example.Quora.Exceptions.CommentNotFoundException;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Repositories.CommentRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	public Comment createComment(final Comment comment) throws InvalidInputException {
		if (comment.getComment() == null || StringUtils.isBlank(comment.getComment())) {
			throw new InvalidInputException("Invalid Comment content.");
		}

		final Comment newComment = commentRepository.save(comment);

		return newComment;
	}

	public Comment getCommentByCommentId(final int commentId) throws InvalidInputException, CommentNotFoundException {
		if (commentId <= 0) {
			throw new InvalidInputException("Invalid Comment ID.");
		}

		final Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new CommentNotFoundException("Comment with id " + commentId + " not found."));

		return comment;
	}

	public Comment deleteComment(final int commentId) throws InvalidInputException, CommentNotFoundException {

		final Comment existingComment = getCommentByCommentId(commentId);

		commentRepository.deleteById(commentId);

		return existingComment;
	}
}
