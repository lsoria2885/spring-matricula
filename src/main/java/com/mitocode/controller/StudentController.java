package com.mitocode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.model.Student;
import com.mitocode.service.IStudentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/students")
@RequiredArgsConstructor
public class StudentController {

	@Autowired
	private final IStudentService service;

	@GetMapping("/ordenados/asc")
	public Flux<Student> getAllStudentsByAgeAsc() {
		return service.findAllByOrderByAgeAsc();
	}

	@GetMapping("/ordenados/desc")
	public Flux<Student> getAllStudentsByAgeDesc() {
		return service.findAllByOrderByAgeDesc();
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Student> findAll() {
		return service.findAll();
	}

	@GetMapping(value = "/asc", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Student> findAllByAgeAsc() {
		return service.findAllByOrderByAgeAsc();
	}

	@GetMapping(value = "/desc", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Student> findAllByAgeDesc() {
		return service.findAllByOrderByAgeDesc();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Student> findById(@PathVariable("id") String id) {
		return service.findById(id);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Student> save(@RequestBody Student Student) {
		return service.save(Student);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Student> update(@PathVariable("id") String id, @RequestBody Student Student) {
		Student.setId(id);
		return service.save(Student);
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
		return service.deleteById(id).flatMap(result -> {
			if (result) {
				return Mono.just(ResponseEntity.noContent().build());
			} else {
				return Mono.just(ResponseEntity.notFound().build());
			}
		});

	}
}
