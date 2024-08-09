# Library Management System

## Overview
The Library Management System is a comprehensive solution designed to manage a library's inventory, borrowing process, and user management efficiently. This application provides functionality for managing books, authors, categories, and user interactions within a library.

## Features
- **Book Management**: Add, update, delete, and search for books in the library's inventory.
- - **Category Management**: Organize books into categories for easy searching and management.
- **User Management**: Manage library users, including borrowing and returning books.
- **Borrowing System**: Track which books are borrowed, by whom, and when they are due.
- **Points System**: Evry patron gain points when he/she borrows a book , after having +1000 point we give him a free borrow
- **Scheduled Daily Job**: Daily job runs at 12PM (you can adjust it in the code) to print all books that should be returned today and the patron contact info if yoiu need to call him/her

## Technologies Used
- **Java**: The core programming language for the application.
- **Spring Boot**: Framework used for building the backend REST API.
- **Spring Data JPA**: For database interaction and ORM mapping.
- **MySQL**: The database used for storing library data.
- **JUnit**: For writing and running unit tests.
- **Maven**: For project build and dependency management.

## Installation and Setup

### Prerequisites
- Java 17
- Maven 3.6+
- MySQL Database

### Steps to Run the Application
1. **Clone the repository**:
    ⁠bash
   git clone https://github.com/MahmoudGhonemy/Library-System-.git
   cd Library-System-
   

⁠ 2. **Set up the database**:
   - Create a MySQL database named `library_system`.
   - Update the `src/main/resources/application.properties` file with your MySQL credentials:
      ⁠properties
     spring.datasource.url=jdbc:mysql://localhost:3306/library_system
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     

⁠ 3. **Build the project**:
    ⁠bash
   mvn clean install
   

⁠ 4. **Run the application**:
    ⁠bash
   mvn spring-boot:run
   

⁠ 5. **Access the application**:
   - Open your browser and go to `http://localhost:8080`.
   - or you can access it by the postman collection that I have attache to the repo and it have its documentation .

## Running Tests
To run the unit tests, you can use the following command:
 ⁠bash
mvn test


## Contributing
Contributions are welcome! Please submit a pull request with your changes, and make sure to include tests.

