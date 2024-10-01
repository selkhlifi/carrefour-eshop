# 🛒 Online Shopping - Reactive eShop

## Overview

This project implements a reactive online shopping platform using:

- **Spring WebFlux**: For non-blocking, asynchronous HTTP requests.
- **MongoDB**: As the reactive NoSQL database.
- **Kafka**: For event-driven communication between services.

## Architecture

The application is designed with a reactive architecture to handle high concurrency, making it scalable and responsive. Kafka facilitates real-time communication for order processing and inventory updates.

### Key Features

1. **Cart Management**: Add or remove products from the cart.
2. **Order Management**: Confirm and track order status.
3. **Inventory Management**: Reflect changes based on user actions.

## Getting Started

1. **Prerequisites**: Java 21, MongoDB, Kafka.
2. **Build**: 
   ```bash
   mvn clean install
   ```
3. **Run**:
   ```bash
   mvn spring-boot:run
   ```
4. **API Access**: `http://localhost:8080/`.

## Future Enhancements

- Caching layer (e.g., Redis).
- CI/CD pipeline.
- Containerization using Docker and Kubernetes.
