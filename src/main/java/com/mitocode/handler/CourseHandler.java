package com.mitocode.handler;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.dto.CourseDTO;
import com.mitocode.model.Course;
import com.mitocode.service.ICourseService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CourseHandler {

	private final ICourseService service;
	private final ModelMapper modelMapper;

	public Mono<ServerResponse> findAll(ServerRequest req) {
		Flux<CourseDTO> body = service.findAll().map(e -> modelMapper.map(e, CourseDTO.class));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(body, CourseDTO.class);
	}

	public Mono<ServerResponse> findById(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.findById(id).map(e -> modelMapper.map(e, CourseDTO.class))
				.flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> save(ServerRequest req) {
		Mono<CourseDTO> dtoMono = req.bodyToMono(CourseDTO.class);
		return dtoMono.map(dto -> modelMapper.map(dto, Course.class)).flatMap(service::save)
				.map(e -> modelMapper.map(e, CourseDTO.class))
				.flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
	}

	public Mono<ServerResponse> update(ServerRequest req) {
		String id = req.pathVariable("id");
		Mono<CourseDTO> dtoMono = req.bodyToMono(CourseDTO.class);

		return service.findById(id).flatMap(db -> dtoMono.doOnNext(dto -> dto.setId(id)))
				.map(dto -> modelMapper.map(dto, Course.class)).flatMap(service::save)
				.map(e -> modelMapper.map(e, CourseDTO.class))
				.flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> delete(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.deleteById(id)
				.flatMap(deleted -> deleted ? ServerResponse.noContent().build() : ServerResponse.notFound().build());
	}
}