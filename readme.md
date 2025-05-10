# Social Media Blog API

## Project Description

This project is a backend REST API for a social media micro-blogging application. It manages user accounts and messages, allowing users to register, log in, post messages, update or delete their messages, and view messages from all users or a specific user. The API is built using Java, Javalin, and an H2 in-memory database, following a three-layer architecture (Controller, Service, DAO).

## Technologies Used

- Java 11
- Javalin 5.0.1
- H2 Database 2.1.214
- Jackson Databind 2.14.0-rc1
- JUnit 4.13.2
- Mockito 4.9.0
- Maven

## Features

- Register new user accounts with unique usernames and secure passwords
- Log in with existing user credentials
- Create new messages (posts) as a registered user
- Retrieve all messages or a specific message by ID
- Retrieve all messages posted by a specific user
- Update the text of an existing message
- Delete a message by its ID

## Database Schema

**Account**

```
account_id integer primary key auto_increment,
username varchar(255) unique,
password varchar(255)
```

**Message**

```
message_id integer primary key auto_increment,
posted_by integer,
message_text varchar(255),
time_posted_epoch long,
foreign key (posted_by) references Account(account_id)
```

## Project Structure

- `src/main/java/Controller/` - API endpoint controllers (e.g., `SocialMediaController.java`)
- `src/main/java/DAO/` - Data access objects for database interaction
- `src/main/java/Model/` - Data models (`Account.java`, `Message.java`)
- `src/main/java/Service/` - Business logic layer
- `src/main/java/Util/` - Utilities (e.g., `ConnectionUtil.java`)
- `src/test/java/` - JUnit test classes for all major features
- `src/main/resources/SocialMedia.sql` - Database schema

## To-do list

- Add user session management and authentication tokens
- Implement pagination for message retrieval endpoints
- Add user profile editing features
- Enhance error handling and validation feedback
- Integrate with a frontend client
- Add API documentation (Swagger/OpenAPI)
- Improve logging and monitoring
- Deploy to a cloud platform

## Getting Started

### Clone the repository

**Windows:**

```
git clone https://github.com/arod1104/arod1104-pep-project.git
```

**Unix/Mac:**

```
git clone https://github.com/arod1104/arod1104-pep-project.git
```

### Environment Setup

1. Ensure you have Java 11 and Maven installed.
2. Navigate to the project directory:
   - Windows: `cd arod1104-pep-project`
   - Unix/Mac: `cd arod1104-pep-project`
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn exec:java -Dexec.mainClass="Main"
   ```
   Or run the `Main` class from your IDE.

### Running Tests

To run all tests and check code coverage:

```
mvn test
```

## Usage

After starting the application, the API will be available at `http://localhost:8080`.

### Example Endpoints

- **Register:** `POST /register`  
  Request body:

  ```json
  { "username": "user", "password": "password" }
  ```

- **Login:** `POST /login`  
  Request body:

  ```json
  { "username": "user", "password": "password" }
  ```

- **Create Message:** `POST /messages`  
  Request body:

  ```json
  {
    "posted_by": 1,
    "message_text": "Hello world!",
    "time_posted_epoch": 1669947792
  }
  ```

- **Get All Messages:** `GET /messages`

- **Get Message by ID:** `GET /messages/{message_id}`

- **Update Message:** `PATCH /messages/{message_id}`  
  Request body:

  ```json
  { "message_text": "Updated message" }
  ```

- **Delete Message:** `DELETE /messages/{message_id}`

- **Get All Messages by User:** `GET /accounts/{account_id}/messages`

## Testing & Quality Metrics

- The project uses JUnit and Mockito for unit and integration testing (see `src/test/java/`).
- Maven Surefire plugin runs all tests and reports results.
- Code coverage can be measured with additional Maven plugins (e.g., JaCoCo).
- All major features are covered by automated tests (see test class names in `src/test/java/`).
- Static analysis and code quality tools are recommended for CI/CD pipelines.

## Contributors

- [arod1104](https://github.com/arod1104)
