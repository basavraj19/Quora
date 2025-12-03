package com.example.Quora.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Quora.Entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	@Query("SELECT q.createdBy FROM Question q WHERE id = :qId")
	String findCreatedBy(@Param("qId") int qId);
}
