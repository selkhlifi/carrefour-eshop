
# 🛒 Online Shopping - Reactive eShop

## Overview

This project implements a reactive online shopping platform using:

- **Spring WebFlux**: For non-blocking, asynchronous HTTP requests.
- **MongoDB**: As the reactive NoSQL database.
- **Kafka**: For event-driven communication between services.
- **Spring Security**: For securing the application using JWT-based authentication.

## Architecture

The application is designed with a reactive architecture to handle high concurrency, making it scalable and responsive. Kafka facilitates real-time communication for order processing and inventory updates.

### Key Features

1. **Cart Management**: Add or remove products from the cart.
2. **Order Management**: Confirm and track order status.
3. **Inventory Management**: Reflect changes based on user actions.
4. **Security**: JWT-based authentication to secure API endpoints.
5. 
## Security Implementation

The application uses **Spring Security** for authentication and authorization. The choice of Spring Security is based on its compatibility with reactive applications using Spring WebFlux and its flexibility in configuring different security mechanisms.

### JWT Authentication

- The application generates a JWT (JSON Web Token) for user authentication.
- The JWT is created when a user accesses the `GET /api/v1/auth` endpoint with valid credentials (username and password).
- Once the JWT is issued, it should be included in the `Authorization` header of subsequent API requests in the format: `Bearer <JWT_TOKEN>`.

### How JWT is Generated

To generate a JWT, the user needs to send a GET request to the `/api/v1/auth` endpoint with the `username` and `password` as query parameters.

## Getting Started

### Prerequisites

Ensure you have the following installed:
- **Java 21**
- **Maven**
- **Docker & Docker Compose**

### Build

To build the project, run the following command:

```bash
mvn clean install
```

### Run the Application

Start the Spring Boot application using:

```bash
mvn spring-boot:run
```

### Running Kafka Locally with Docker Compose

To run Kafka and Zookeeper locally, ensure you have the Docker file set up in your project. Then, use the following command:

```bash
docker-compose up -d
```

### Testing with Postman

You can test the APIs using the provided Postman collection that is included in the project. Follow these steps:

1. Import the `carrefour-eshop.postman_collection.json` file into Postman.
2. Ensure the Spring Boot application is running locally on `http://localhost:8080`.
3. Use the collection to send requests to the different endpoints, such as adding items to the cart, placing an order, or generating a JWT token using the /api/v1/auth endpoint.

### API Documentation

The API endpoints are documented using Swagger. Once the application is running, you can access the API documentation at:

```
http://localhost:8080/api-docs/
```

## Future Enhancements

- Implement a caching layer (e.g., Redis).
- Setup a CI/CD pipeline for automated testing and deployment.
- Containerization using Docker and deployment on Kubernetes.

