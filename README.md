#  Distributed File Managment Service

This is a File Manager application built with Spring Boot, Hibernate, and JWT-based Spring Security. The application allows multiple users to manage their own files and folders securely. It provides RESTful endpoints for user authentication, file operations, directory management, and other CRUD operations.

## Features

- User Sign Up: New users can register for an account.
- User Sign In: Registered users can authenticate and obtain a JWT token.
- File Upload: Users can upload files to their personal storage.
- Directory Creation: Users can create new directories/folders.
- File and Directory Listing: Users can view their files and directories.
- File Download: Users can download their files.
- File Deletion: Users can delete their files.
- Directory Deletion: Users can delete their directories and all the files within them.
- Swagger UI: Explore the available API endpoints using Swagger UI. You can access it at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

## Prerequisites

Before running the application, ensure that you have the following prerequisites installed:

- Java Development Kit (JDK) 8 or higher
- Gradle
- MySQL or any other supported database

## Setup

1. Clone the repository:

   ```
   git clone https://github.com/your-username/file-manager.git
   ```

2. Navigate to the project directory:

   ```
   cd file-manager
   ```
   
3. Update the database configuration:

   - Open the `src/main/resources/application.properties` file.
   - Modify the database connection properties, such as `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password`, according to your database setup.

4. Build the application using Gradle:

   ```
   gradle bootrun
   ```
5. Access the application:

   Open your web browser and visit [http://localhost:8080](http://localhost:8080) to access the File Manager application.

## API Documentation

The API endpoints and their usage are documented using Swagger UI. You can explore the available endpoints and make API requests by following these steps:

1. Start the application as described in the Setup section.
2. Open your web browser and visit [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
3. You will see the Swagger UI interface with the available endpoints categorized by functionality.
4. Click on an endpoint to expand it and view details such as HTTP methods, request/response bodies, and example requests.
5. Use the provided user authentication endpoints to obtain an access token (JWT) for authorization.
6. Once you have the access token, you can use it in the "Authorize" button on the top-right corner of the Swagger UI page to authenticate your requests.
7. Make API requests by filling in the required parameters and clicking "Try it out!".

## Security

This application uses JWT-based authentication and authorization with Spring Security to secure the endpoints. Users need to sign up, obtain a JWT token through the authentication endpoint, and include the token in the request headers for protected endpoints.

## License

This File Manager application is open-source and distributed under the MIT License. See the [LICENSE](LICENSE) file for more information.

## Contact

If you have any questions
