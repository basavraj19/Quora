package com.example.Quora.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Quora.Entities.Dislike;

@Repository
public interface DislikeRepository extends JpaRepository<Dislike, Integer> {

	@Query("SELECT count(1) FROM Dislike WHERE answerId = :ansId")
	int findDisLikeCountByAnswerId(int ansId);

}
