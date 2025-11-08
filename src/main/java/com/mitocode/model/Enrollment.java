package com.mitocode.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "enrollments")
public class Enrollment {

	@Id
	@EqualsAndHashCode.Include
	private String id;

	@Field
	private LocalDateTime dateEnrollment;

	@Field
	private Student student;

	@Field
	private List<Course> courses;

	@Field
	private boolean enabled;

}
