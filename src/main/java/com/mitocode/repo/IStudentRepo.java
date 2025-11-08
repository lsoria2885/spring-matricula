package com.mitocode.repo;

import com.mitocode.model.Student;

import reactor.core.publisher.Flux;

public interface IStudentRepo extends IGenericRepo<Student, String> {

	Flux<Student> findAllByOrderByAgeAsc();

	Flux<Student> findAllByOrderByAgeDesc();
}
