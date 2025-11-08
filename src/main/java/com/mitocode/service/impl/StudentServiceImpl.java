package com.mitocode.service.impl;

import org.springframework.stereotype.Service;

import com.mitocode.model.Student;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IStudentRepo;
import com.mitocode.service.IStudentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Student, String> implements IStudentService {

	private final IStudentRepo repo;

	@Override
	protected IGenericRepo<Student, String> getRepo() {
		return repo;
	}

	@Override
	public Flux<Student> findAllByOrderByAgeAsc() {
		return repo.findAllByOrderByAgeAsc();
	}

	@Override
	public Flux<Student> findAllByOrderByAgeDesc() {
		return repo.findAllByOrderByAgeDesc();
	}
}
