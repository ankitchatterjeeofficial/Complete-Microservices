# User-Hotel-Rating Microservices Architecture

## Overview

This project implements a microservices architecture for user, hotel, and rating management. It consists of several microservices, each responsible for specific functionalities. The system utilizes JPA/Hibernate for ORM with different databases for each service, and communication between microservices is facilitated using Netflix Eureka Discovery Server. Additionally, an API gateway microservice provides a single entry point for accessing the different microservices. Spring Security with OKTA is implemented for token generation, passing, and validation. Resilience4j library is integrated for implementing circuit breakers to enhance system resilience. Pagination is implemented for improved web page viewing, and global exception handling ensures robust error management.

## Microservices

1. **User Service**: Handles user creation, updating, fetching, and searching by name. Utilizes SQL database for storage.
2. **Hotel Service**: Manages hotel information, including creation, updating, fetching, and searching by name. PostgreSQL database is used for storage.
3. **Rating Service**: Deals with rating creation, updating, and fetching. MongoDB with MongoTemplate is employed for storage.
4. **API Gateway**: Provides a single URL for accessing the microservices, enhancing the system's scalability and maintainability.
5. **Config Server**: Manages configuration settings for the User, Rating, and Hotel Services via GitHub server.

## Technology Stack

- Backend: Java
- Framework: Spring Boot
- Databases: SQL, PostgreSQL, MongoDB
- ORM: JPA/Hibernate
- Microservices
- Security: OKTA
- Resilience: Resilience4j
- Version Control: Git

## Features

- Microservices architecture for modularization and scalability
- JPA/Hibernate for object-relational mapping
- Netflix Eureka for microservice discovery and registration
- Spring Security with OKTA for secure authentication and authorization
- Resilience4j for implementing circuit breakers, enhancing system resilience
- Pagination for improved web page viewing
- Global exception handling for robust error management
