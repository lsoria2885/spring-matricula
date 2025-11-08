package com.mitocode.security;

import com.fasterxml.jackson.annotation.JsonProperty;

//Clase S3
public record AuthResponse(@JsonProperty(value = "access_token") String token) {
}
