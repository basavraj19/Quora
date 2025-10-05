package com.example.Quora.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Quora_Comment_Dtl")
public class Comment extends BaseModel {

	@Column(nullable = false)
	private String comment;

	@ManyToOne
	@JoinColumn(name = "answerId", nullable = false)
	@JsonBackReference
	private Answer answer;
}
