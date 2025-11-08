package com.mitocode.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.handler.CourseHandler;
import com.mitocode.handler.EnrollmentHandler;
import com.mitocode.handler.StudentHandler;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> studentRoutes(StudentHandler h){
        return route(GET("/v1/students"), h::findAll)
            .andRoute(GET("/v1/students/{id}"), h::findById)
            .andRoute(POST("/v1/students"), h::save)
            .andRoute(PUT("/v1/students/{id}"), h::update)
            .andRoute(DELETE("/v1/students/{id}"), h::delete)
            .andRoute(GET("/v1/students/edad/asc"), h::findAllByAgeAsc)
            .andRoute(GET("/v1/students/edad/desc"), h::findAllByAgeDesc);
    }

    @Bean
    public RouterFunction<ServerResponse> courseRoutes(CourseHandler h){
        return route(GET("/v1/courses"), h::findAll)
            .andRoute(GET("/v1/courses/{id}"), h::findById)
            .andRoute(POST("/v1/courses"), h::save)
            .andRoute(PUT("/v1/courses/{id}"), h::update)
            .andRoute(DELETE("/v1/courses/{id}"), h::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> enrollmentRoutes(EnrollmentHandler h){
        return route(GET("/v1/enrollments"), h::findAll)
            .andRoute(GET("/v1/enrollments/{id}"), h::findById)
            .andRoute(POST("/v1/enrollments"), h::save)
            .andRoute(PUT("/v1/enrollments/{id}"), h::update)
            .andRoute(DELETE("/v1/enrollments/{id}"), h::delete);
    }
}
