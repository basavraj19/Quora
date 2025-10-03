package com.example.Quora.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Quora.Entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
