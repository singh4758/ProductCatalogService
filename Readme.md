# Product Catalog Service

A Spring Boot application that manages product information and integrates with external services. The service provides both a fake store API client implementation and a database-backed implementation.

## Overview

The Product Catalog Service is a RESTful API that allows for:
- Managing product information (CRUD operations)
- Integration with an external fake store API
- User-based product detail retrieval

## Architecture

The application follows a layered architecture:
- **Controller layer** (implied): Handles HTTP requests
- **Service layer**: Business logic implementation
- **Repository layer**: Database access
- **Client layer**: External API integrations
- **Model layer**: Domain objects
- **DTO layer**: Data transfer objects

## Key Components

### Models
- `Product`: Main entity for product information
    - Properties: id, name, price, description, imageUrl, category
- `Category`: Product category information

### Services
- `IProductService`: Interface defining product operations
- `ProductService`: Implementation using FakeStore API
- `StorageProductService`: Primary implementation using database storage

### Repository
- `ProductRepository`: JPA repository for product persistence
    - Extends JpaRepository with additional query methods

### External Integrations
- FakeStore API client for product data
- User Service API for user information

## Service Implementations

### StorageProductService (Primary)
The main implementation that stores products in a database:
- Complete CRUD operations for products
- Uses ProductRepository for database operations
- Properly handles partial updates for product fields
- Tagged as `@Primary` to be the default implementation

### ProductService
Alternative implementation that delegates to the FakeStore API:
- Maps between application domain models and FakeStore DTOs
- Integrates with UserService for retrieving user information
- Uses RestTemplate for HTTP communication

## Configuration

The application uses externalized configuration:
- `userServiceUrl`: URL for the user service API

## Usage

The application provides the following functionality:
- Get all products
- Get product by ID
- Create new products
- Update existing products
- Delete products
- Get product details with user information

## Technology Stack

- Java
- Spring Boot
- Spring Data JPA
- Maven
- RESTful API design
- External API integration

## Repository Information

- Repository URL: https://github.com/singh4758/ProductCatalogService.git
- Current branch: develop