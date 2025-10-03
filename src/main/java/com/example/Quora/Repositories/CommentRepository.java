package com.example.Quora.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Quora.Entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
