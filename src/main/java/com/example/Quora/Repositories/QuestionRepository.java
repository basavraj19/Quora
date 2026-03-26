package com.example.Quora.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Quora.Entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	@Query("SELECT q.createdBy FROM Question q WHERE id = :qId")
	String findCreatedBy(@Param("qId") int qId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM quora_question_dtl where question"
			+ " like CONCAT('%', :question, '%')")
	List<Question> getQuestionByContent(@Param("question") String question);
}
