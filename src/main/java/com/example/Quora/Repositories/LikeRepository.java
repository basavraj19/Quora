package com.example.Quora.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Quora.Entities.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

	@Query("SELECT count(1) FROM Like WHERE answerId = :ansId")
	int findLikeCountByAnswerId(@Param("ansId") int ansId);
}
