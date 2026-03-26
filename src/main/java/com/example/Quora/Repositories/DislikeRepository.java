package com.example.Quora.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Quora.Entities.Dislike;

@Repository
public interface DislikeRepository extends JpaRepository<Dislike, Integer> {

	@Query("SELECT count(1) FROM Dislike WHERE answerId = :ansId")
	int findDisLikeCountByAnswerId(@Param("ansId") int ansId);

	@Query(nativeQuery = true, value = "SELECT * FROM quora_dislike_dtl where answer_id =:ansId and"
			+ " created_by =:loggedInUser limit 1")
	Dislike isUserDisliked(@Param("ansId") int ansId, @Param("loggedInUser") String loggedInUser);
}
