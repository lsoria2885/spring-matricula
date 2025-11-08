package com.mitocode.handler;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.dto.EnrollmentDTO;
import com.mitocode.model.Enrollment;
import com.mitocode.service.IEnrollmentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EnrollmentHandler {

	private final IEnrollmentService service;
	private final ModelMapper modelMapper;

	public Mono<ServerResponse> findAll(ServerRequest req) {
		Flux<EnrollmentDTO> body = service.findAll().map(e -> modelMapper.map(e, EnrollmentDTO.class));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(body, EnrollmentDTO.class);
	}

	public Mono<ServerResponse> findById(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.findById(id).map(e -> modelMapper.map(e, EnrollmentDTO.class))
				.flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> save(ServerRequest req) {
		Mono<EnrollmentDTO> dtoMono = req.bodyToMono(EnrollmentDTO.class);
		return dtoMono.map(dto -> modelMapper.map(dto, Enrollment.class)).flatMap(service::save)
				.map(e -> modelMapper.map(e, EnrollmentDTO.class))
				.flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
	}

	public Mono<ServerResponse> update(ServerRequest req) {
		String id = req.pathVariable("id");
		Mono<EnrollmentDTO> dtoMono = req.bodyToMono(EnrollmentDTO.class);

		return service.findById(id).flatMap(db -> dtoMono.doOnNext(dto -> dto.setId(id)))
				.map(dto -> modelMapper.map(dto, Enrollment.class)).flatMap(service::save)
				.map(e -> modelMapper.map(e, EnrollmentDTO.class))
				.flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> delete(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.deleteById(id)
				.flatMap(deleted -> deleted ? ServerResponse.noContent().build() : ServerResponse.notFound().build());
	}
}