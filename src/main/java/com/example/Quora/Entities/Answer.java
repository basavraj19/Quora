package com.example.Quora.Entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "Quora_Answer_Dtl")
public class Answer extends BaseModel {

	@Column(nullable = false)
	private String answer;

	@ManyToOne
	@JoinColumn(name = "questionId")
	private Question question;

	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	private List<Like> likes;

	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	private List<Dislike> dislike;
}
