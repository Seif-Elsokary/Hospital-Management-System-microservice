package com.hospital.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args){
		SpringApplication.run(GatewayserverApplication.class, args);
	}

    @Bean
    public RouteLocator hospitalRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(r -> r.path("/api/doctor/**")
                        .filters(f -> f.rewritePath("/api/doctor/(?<segment>.*)", "/api/doctor/${segment}")
                                .addResponseHeader("X-Response-Date"    , LocalDateTime.now().toString()))
                        .uri("lb://DOCTOR"))
                .route(r -> r.path("/api/patient/**")
                        .filters(f -> f.rewritePath("/api/patient/(?<segment>.*)", "/api/patient/${segment}")
                                .addResponseHeader("X-Response-Date"    , LocalDateTime.now().toString()))
                        .uri("lb://PATIENT"))
                .route(r -> r.path("/api/appointment/**")
                        .filters(f -> f.rewritePath("/api/appointment/(?<segment>.*)", "/api/appointment/${segment}")
                                .addResponseHeader("X-Response-Date"    , LocalDateTime.now().toString()))
                        .uri("lb://APPOINTMENT"))
                .build();
    }

}
