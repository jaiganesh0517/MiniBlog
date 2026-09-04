# MiniBlog

A minimal blogging platform built with Spring Boot, designed to demonstrate a complete, correctly-layered backend architecture — from data modeling through to JWT-based authentication.

This project was intentionally kept small in scope. The goal wasn't to build a feature-rich app, but to deeply understand *how* a real backend system works end to end: request → controller → service → repository → database, with real security in between.

## Features

- User registration and login
- JWT-based authentication and authorization
- Create, view, edit, and soft-delete blog posts
- Ownership-based access control (only a post's author can edit or delete it)
- Public post feed, single post view, and "my posts" view
- Passwords hashed with BCrypt — never stored in plain text

## Tech Stack

- **Java** / **Spring Boot**
- **Spring Web** — REST API
- **Spring Data JPA** / **Hibernate** — ORM and persistence
- **MySQL** — relational database
- **Spring Security** — authentication and authorization
- **JWT (jjwt)** — stateless token-based authentication
- **Maven** — dependency and build management
- **Docker** / **Docker Compose** — containerized app and database

## Architecture

The project follows a standard layered architecture:

```
Controller  →  Service  →  Repository  →  Database
```

- **Controller** — handles HTTP requests/responses only. No business logic.
- **Service** — contains all business rules (ownership checks, soft-delete logic, password hashing, token generation).
- **Repository** — Spring Data JPA interfaces responsible for database access.
- **Entity** — maps directly to database tables via Hibernate.

## Key Design Decisions

**Soft delete, not hard delete.** Deleting a post doesn't remove it from the database — it sets a `deleted` timestamp instead. This preserves a record trail while hiding the post from all read queries. Simpler to reason about than a separate status flag, and avoids a class of "conflicting data" bugs.

**Ownership is never trusted from client input.** When creating, editing, or deleting a post, the acting user's identity always comes from their verified JWT token — never from a field the client could tamper with in the request body.

**Server-controlled timestamps.** `dateCreated`, `dateLastEdited`, and `deleted` are always set by the server (`LocalDateTime.now()`), never accepted from client input, to prevent tampering.

**Stateless authentication.** JWTs carry the user's identity, verified via a custom `OncePerRequestFilter` on every request. No server-side session state.

**App and database run as separate containers.** Rather than bundling everything into one container, the Spring Boot app and MySQL each run in their own container with a single responsibility, communicating over Docker's internal network by service name. Database connection values are injected via environment variables (with local defaults as fallbacks), so the same codebase runs unmodified whether it's started locally or via Docker.

## API Endpoints

| Method | Endpoint       | Description                  | Auth Required |
|--------|----------------|-------------------------------|---------------|
| POST   | `/users`       | Register a new user           | No            |
| POST   | `/users/login` | Log in, returns a JWT         | No            |
| GET    | `/posts`       | View public feed              | No            |
| GET    | `/posts/{id}`  | View a single post            | No            |
| GET    | `/posts/me`    | View your own posts           | Yes           |
| POST   | `/posts`       | Create a new post             | Yes           |
| PUT    | `/posts/{id}`  | Edit your own post            | Yes           |
| DELETE | `/posts/{id}`  | Soft-delete your own post     | Yes           |

For protected endpoints, include a header: `Authorization: Bearer <token>`

## Getting Started

There are two ways to run MiniBlog: with Docker (no local Java/MySQL setup needed), or manually against your own local environment.

### Option A: Docker (recommended)

**Prerequisites:** Docker and Docker Compose installed.

1. Clone the repository
   ```
   git clone <your-repo-url>
   cd MiniBlog
   ```

2. Build the application jar
   ```
   ./mvnw clean package
   ```

3. Start the app and a MySQL container together
   ```
   docker compose up --build
   ```

Docker Compose spins up two containers — the Spring Boot app and a MySQL 8.0 instance — connected over an internal Docker network. The app waits for MySQL to pass a health check before starting, and Hibernate creates the schema automatically on first run. No local MySQL installation or manual `application.properties` setup is required; the database connection is injected via environment variables defined in `docker-compose.yml`.

The app will be available at `http://localhost:8080`.

> Note: the MySQL credentials in `docker-compose.yml` only exist inside Docker's isolated network and are not reachable from outside it — they're intentionally separate from any real local database credentials.

### Option B: Manual (local Java + MySQL)

**Prerequisites:**
- Java 17+ (or your configured JDK version)
- Maven
- MySQL running locally

**Setup:**

1. Clone the repository
   ```
   git clone <your-repo-url>
   cd MiniBlog
   ```

2. Create a MySQL database named `miniblogdb`

3. Copy the example config and fill in your own values:
   ```
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
   Then edit `application.properties` with your MySQL credentials and a JWT secret (32+ random characters recommended).

4. Run the application
   ```
   ./mvnw spring-boot:run
   ```

The app will start on `http://localhost:8080`, and Hibernate will automatically create the required tables.

## Example Usage

**Register**
```
POST /users
{
  "userName": "jai",
  "emailId": "jai@example.com",
  "password": "securepassword"
}
```

**Login**
```
POST /users/login
{
  "emailId": "jai@example.com",
  "password": "securepassword"
}
```
Returns a JWT token to use for protected endpoints.

**Create a post**
```
POST /posts
Authorization: Bearer <token>
{
  "title": "My first post",
  "content": "Hello, MiniBlog!"
}
```

## Known Limitations / Roadmap

This project deliberately keeps a narrow scope to focus on core architecture. Not yet implemented:

- Proper HTTP error responses (currently returns `null`/empty body on failed ownership checks instead of a `403`)
- Post search
- Report/moderation system for harmful content
- User profile pictures and bios
- Recovering your own soft-deleted posts

## License

This project is for educational purposes.
