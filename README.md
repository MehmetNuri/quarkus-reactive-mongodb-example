
# Quarkus Reactive MongoDB Example

This project provides a REST API example using Quarkus with a reactive MongoDB client. The project stores user data in MongoDB and provides access to this data through various API endpoints.

## Technologies Used

| Technology             | Version        |
|------------------------|----------------|
| Quarkus                | 3.12.3         |
| Java                   | 21             |
| MongoDB                | 4.4            |
| Hibernate Reactive     | 1.0            |
| RestEasy Reactive      | 2.0            |
| MapStruct              | 1.4.2.Final    |

## API Endpoints

### User Operations

| HTTP Method | Endpoint                          | Description                                      |
|-------------|-----------------------------------|--------------------------------------------------|
| GET         | `/users`                          | Retrieves all users with pagination.             |
| GET         | `/users/find/{id}`                | Retrieves a user by ID.                          |
| GET         | `/users/name/{userName}`          | Retrieves users by name with pagination.         |
| GET         | `/users/age/{age}`                | Retrieves users by age with pagination.          |
| GET         | `/users/email-domain/{domain}`    | Retrieves users by email domain with pagination. |
| POST        | `/users/create`                   | Creates a new user.                              |
| PUT         | `/users/update/{id}`              | Updates an existing user.                        |
| DELETE      | `/users/delete/{id}`              | Deletes a user by ID.                            |

## Setup

Follow these steps to set up and run the project:

1. **Install project dependencies:**

   ```shell
   mvn clean install
   ```

2. **Start the application:**

   ```shell
   ./mvnw quarkus:dev
   ```

3. **Verify the application is running:**

   Open your browser and go to `http://localhost:8080`.

## Database Configuration

Add the following MongoDB connection settings to the `application.properties` file:

```properties
quarkus.mongodb.connection-string = mongodb://localhost:27017
quarkus.mongodb.database = yourDatabaseName
```

## Error Handling

The project uses centralized error handling to catch errors such as 404 and 500, and returns a structured error response. Error responses are returned in the following format:

```json
{
    "errorId": "unique-error-id",
    "errors": ["Error message"],
    "message": "General error message",
    "statusCode": 404,
    "timestamp": "2024-07-18T13:03:01.637531335"
}
```

## Pagination

The API endpoints support pagination. The page and size query parameters determine the page and the number of items per page. For example, `/users?page=1&size=10` returns the second page with 10 users.

## Model

The user model includes the following fields:

- `id`: The unique identifier of the user
- `name`: The name of the user
- `age`: The age of the user
- `email`: The email address of the user
- `address`: The address of the user
- `createdDate`: The creation date of the user

## DTO

The UserDTO (Data Transfer Object) includes the following fields:

- `id`: The unique identifier of the user
- `name`: The name of the user
- `age`: The age of the user
- `email`: The email address of the user
- `address`: The address of the user

## Mapper

MapStruct is used to transform between the user model and the DTO.

## Development

If you would like to contribute to the project, please fork the repository and submit a pull request with your changes. If you have any questions or suggestions, feel free to contact us.
