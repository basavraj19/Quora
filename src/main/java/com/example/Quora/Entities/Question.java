package com.example.Quora.Entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Quora_Question_Dtl")
public class Question extends BaseModel {

	@Column(nullable = false)
	private String question;

	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	private List<Answer> answers;
}
