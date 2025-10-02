package com.example.Quora.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {

	@Column(nullable = false, unique = true)
	private String userName;

	private String firstName;

	private String lastName;

	@Column(nullable = false)
	private String password;
}
