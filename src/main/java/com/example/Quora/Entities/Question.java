package com.example.Quora.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Quora_Question_Dtl")
public class Question extends BaseModel {

	@Column(nullable = false)
	private String question;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	@JsonBackReference
	private User user;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	@JsonManagedReference 
	private List<Answer> answers;
}
