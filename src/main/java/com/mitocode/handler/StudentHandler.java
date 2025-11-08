package com.mitocode.handler;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.dto.StudentDTO;
import com.mitocode.model.Student;
import com.mitocode.service.IStudentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StudentHandler {

	private final IStudentService service;
	private final ModelMapper modelMapper;

	public Mono<ServerResponse> findAll(ServerRequest req) {
		Flux<StudentDTO> body = service.findAll().map(e -> modelMapper.map(e, StudentDTO.class));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(body, StudentDTO.class);
	}

	public Mono<ServerResponse> findById(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.findById(id).map(e -> modelMapper.map(e, StudentDTO.class))
				.flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> save(ServerRequest req) {
		Mono<StudentDTO> dtoMono = req.bodyToMono(StudentDTO.class);
		return dtoMono.map(dto -> modelMapper.map(dto, Student.class)).flatMap(service::save)
				.map(e -> modelMapper.map(e, StudentDTO.class))
				.flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
	}

	public Mono<ServerResponse> update(ServerRequest req) {
		String id = req.pathVariable("id");
		Mono<StudentDTO> dtoMono = req.bodyToMono(StudentDTO.class);

		return service.findById(id).flatMap(db -> dtoMono.doOnNext(dto -> dto.setId(id)))
				.map(dto -> modelMapper.map(dto, Student.class)).flatMap(service::save)
				.map(e -> modelMapper.map(e, StudentDTO.class))
				.flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> delete(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.deleteById(id)
				.flatMap(deleted -> deleted ? ServerResponse.noContent().build() : ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> findAllByAgeAsc(ServerRequest req) {
		Flux<StudentDTO> body = service.findAllByOrderByAgeAsc().map(e -> modelMapper.map(e, StudentDTO.class));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(body, StudentDTO.class);
	}

	public Mono<ServerResponse> findAllByAgeDesc(ServerRequest req) {
		Flux<StudentDTO> body = service.findAllByOrderByAgeDesc().map(e -> modelMapper.map(e, StudentDTO.class));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(body, StudentDTO.class);
	}
}
