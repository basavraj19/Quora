package com.example.Quora.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.DTO.CommentDto;
import com.example.Quora.Entities.Answer;
import com.example.Quora.Entities.Comment;
import com.example.Quora.Exceptions.CommentNotFoundException;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Exceptions.UnauthorizedException;
import com.example.Quora.Repositories.AnswerRepository;
import com.example.Quora.Repositories.CommentRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private UserService userService;

	@Autowired
	private AnswerRepository answerRepository;

	public Comment createComment(final CommentDto commentDto) throws InvalidInputException {
		if (commentDto.getComment() == null || StringUtils.isBlank(commentDto.getComment())) {
			throw new InvalidInputException("Invalid Comment content.");
		}

		final Answer answer = answerService.getAnswerByAnswerId(commentDto.getAnsId());

		final Comment comment = Comment.builder().comment(commentDto.getComment()).answer(answer).build();

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
		final String loggedInUsername = userService.getLoggedInUserName();
		final boolean isAdmin = userService.isloggedInUserAdmin();

		final Comment existingComment = getCommentByCommentId(commentId);

		final String answerCreatedBy = answerRepository.findCreatedBy(existingComment.getAnswer().getId());

		if (!(loggedInUsername.equals(existingComment.getCreatedBy()) || loggedInUsername.equals(answerCreatedBy)
				|| isAdmin)) {
			throw new UnauthorizedException("You are not authorised to perform delete operation.");
		}
		commentRepository.deleteById(commentId);

		return existingComment;
	}
}
