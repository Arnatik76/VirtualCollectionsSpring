# Virtual Collections Spring

**Virtual Collections Spring** is the server part of the virtual collections management platform. The project provides a RESTful API for working with collections, media elements, users, and tags.

## Main features

- Collection management (create, update, delete, add items).
- Media element management.
- Manage user achievements.
- Search by collections, media, and users.
- Support for users and their subscriptions.

## Technologies used in the project

- **Language:** Java 21
- **Framework:** Spring Boot 3.2.5
    - Spring Data JPA for working with the database.
    - Spring Security to implement security.
    - Spring Web for processing HTTP requests.
    - Spring Actuator for monitoring the application status.
- **Database:** PostgreSQL
- **API Documentation:** Springdoc OpenAPI + Swagger UI
- **Assembly System:** Gradle
- **Lombok:** to shorten the template code.

## Installation and launch

1. Clone the repository:
   `git clone https://github.com/Arnatik76/VirtualCollectionsSpring.git`

2. Go to the project folder:
   `cd VirtualCollectionsSpring`

3. Configure the database connection in the application.properties file.
4. Assemble the project and run:
`./gradlew bootRun`
