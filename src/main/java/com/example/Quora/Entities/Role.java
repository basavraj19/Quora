package com.example.Quora.Entities;

import java.util.Set;

import com.example.Quora.Utils.RoleEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "quora_role_dtl")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Role extends BaseModel {

	@Column(nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private RoleEnum role;

	@ManyToMany(mappedBy = "roles")
	@JsonBackReference(value = "user-role")
	private Set<User> users;
}
