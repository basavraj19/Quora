package com.example.Quora.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Quora.Entities.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	@Query("SELECT a.createdBy From Answer a WHERE a.id = :ansId")
	String findCreatedBy(@Param("ansId") int ansId);
}
