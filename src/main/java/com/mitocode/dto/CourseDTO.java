package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {

	private String id;

	@NotNull
	@Size(min = 2, max = 20)
	private String name;

	@Size(min = 1, max = 3)
	private String initial;

	@NotNull
	private Boolean enabled;
}
