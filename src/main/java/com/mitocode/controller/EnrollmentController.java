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

import com.mitocode.model.Enrollment;
import com.mitocode.service.IEnrollmentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

	@Autowired
	private final IEnrollmentService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Enrollment> findAll() {
		return service.findAll();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Enrollment> findById(@PathVariable("id") String id) {
		return service.findById(id);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Enrollment> save(@RequestBody Enrollment Enrollment) {
		return service.save(Enrollment);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Enrollment> update(@PathVariable("id") String id, @RequestBody Enrollment Enrollment) {
		Enrollment.setId(id);
		return service.save(Enrollment);
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
