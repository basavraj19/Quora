package com.example.Quora.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Quora.Entities.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

}
