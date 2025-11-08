package com.mitocode.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

	private final AuthenticationManager authenticationManager;

	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		return Mono.empty();
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authorization"));
		}

		String token = authHeader.substring(7);
		Authentication auth = new UsernamePasswordAuthenticationToken(token, token);

		return authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
	}
}
