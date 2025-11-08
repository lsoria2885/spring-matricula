package com.mitocode.repo;

import com.mitocode.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserRepo extends IGenericRepo<User, String> {

	Mono<User> findOneByUsername(String username);

	Mono<Void> deleteByUsername(String username);

	Mono<Boolean> existsByUsername(String username);

	Flux<User> findAllByUsername(String username);

	Mono<Void> deleteManyByUsername(String username);

}