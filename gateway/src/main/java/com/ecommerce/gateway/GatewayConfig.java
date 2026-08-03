package com.ecommerce.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service",r ->
                        r.path("/products","/products/**")
                                .filters(f -> f.circuitBreaker( config -> config
                                        .setName("ecomBreaker")
                                        .setFallbackUri("forward:/fallback/products")))
//                        .filters(f -> f.rewritePath("/products(?<segment>/?.*)"
//                                ,"/api/products${segment}"))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("user-service",r ->
                        r.path("/public/users","/public/users/**","/admin/users/**")
//                                .filters(f -> f.rewritePath("/public/users(?<segment>/?.*)",
//                                        "/api/public/users${segment}"))
                                .uri("lb://USER-SERVICE"))
                .route("order-service",r ->
                        r.path("/api/order/**","/api/cart/**")
                        .uri("lb://ORDER-SERVICE"))
                .route("eureka-main",r ->
                        r.path("/eureka/main")
                        .filters(f -> f.rewritePath("/eureka/main", "/"))
                        .uri("http://localhost:8761"))
                .route("eureka-assets",r ->
                        r.path("/eureka/**","/css/**","/js/**","/images/**","/webjars/**","/fonts/**")
                        .uri("http://localhost:8761"))
                .build();
    }
}
