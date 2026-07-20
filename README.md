# E-Commerce Microservices

A modern microservices-based e-commerce platform built with Spring Boot, featuring service discovery, configuration management, and asynchronous messaging.

## Architecture Overview

This project implements a microservices architecture with the following components:

- **Config Server**: Centralized configuration management service
- **Eureka Server**: Service discovery and registration
- **User Service**: Manages user profiles and authentication
- **Product Service**: Handles product catalog and inventory
- **Order Service**: Processes orders and manages order lifecycle

## Technology Stack

- **Framework**: Spring Boot 4.1.0
- **Language**: Java 17
- **Build Tool**: Maven
- **Service Discovery**: Spring Cloud Eureka
- **Configuration Management**: Spring Cloud Config
- **Message Broker**: RabbitMQ 3.13
- **Database**: PostgreSQL 14
- **Database UI**: PgAdmin 4
- **Containerization**: Docker & Docker Compose

## Prerequisites

- Docker & Docker Compose
- Java 17+
- Maven 3.8+

## Getting Started

### 1. Start Infrastructure Services

Run Docker Compose to start all infrastructure services:

```bash
docker-compose up -d
mvn clean install
cd configserver
mvn spring-boot:run
cd eureka
mvn spring-boot:run
cd user/user
mvn spring-boot:run
cd product/product
mvn spring-boot:run
cd order/order
mvn spring-boot:run

Config Server
•
Port: 8888
•
Purpose: Centralized configuration management for all microservices
 Eureka Server
•
Port: 8761
•
UI: http://localhost:8761
•
Purpose: Service registry and discovery
 User Service
•
Port: 8081
•
Purpose: User management and authentication
 Product Service
•
Port: 8082
•
Purpose: Product catalog and inventory management
 Order Service
•
Port: 8083
•
Purpose: Order processing and management


