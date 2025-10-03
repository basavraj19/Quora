package com.example.Quora.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Quora_Comment_Dtl")
public class Comment extends BaseModel{

	@Column(nullable = false)
	private String comment;

	@ManyToOne
	@JoinColumn(name = "answerId")
	private Answer answer;
}
