package com.mitocode.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollmentDTO {

	private String id;

	@NotNull
	private StudentDTO student;

	@NotNull
	private List<CourseDTO> courses;

	private String dateEnrollment;
}
