
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
3. Use the collection to send requests to the different endpoints, such as adding items to the cart or placing an order.

### API Documentation

The API endpoints are documented using Swagger. Once the application is running, you can access the API documentation at:

```
http://localhost:8080/api-docs/
```

## Future Enhancements

- Implement a caching layer (e.g., Redis).
- Setup a CI/CD pipeline for automated testing and deployment.
- Containerization using Docker and deployment on Kubernetes.

