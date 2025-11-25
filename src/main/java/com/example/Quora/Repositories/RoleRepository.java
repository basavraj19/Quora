package com.example.Quora.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Quora.Entities.Role;
import com.example.Quora.Utils.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	public Optional<Role> findByRole(RoleEnum role);
}
