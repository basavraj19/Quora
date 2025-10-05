package com.example.Quora.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

	private String question;

	private String createdBy;

	private String modifiedBy;

	private String userName;

	private String createdAt;

	private String modifiedAt;
}
