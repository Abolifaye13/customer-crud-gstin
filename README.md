# Customer CRUD with GSTIN Validation

A Spring Boot REST API application for managing customer information with GSTIN format validation and role-based access control.

## Features

- Create, read, update, and delete customers.
- GSTIN format validation.
- Store customer name, GSTIN, billing address, and shipping address.
- Admin and User authentication using Spring Security.
- Role-based authorization.
- H2 in-memory database.
- Automated API and security tests.

## Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Jakarta Bean Validation
- H2 Database
- Maven
- Postman

## API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/customers` | Create customer | ADMIN |
| GET | `/api/customers` | Get all customers | ADMIN, USER |
| GET | `/api/customers/{id}` | Get customer by ID | ADMIN, USER |
| PUT | `/api/customers/{id}` | Update customer | ADMIN |
| DELETE | `/api/customers/{id}` | Delete customer | ADMIN |

## Test Credentials

For local testing:

| Role | Username | Password |
|---|---|---|
| ADMIN | `admin` | `admin123` |
| USER | `user` | `user123` |

Use Basic Authentication in Postman.

## Sample Customer JSON

```json
{
  "name": "ABC Technologies",
  "gstin": "27ABCDE1234F1Z5",
  "billingAddress": "Nagpur, Maharashtra",
  "shippingAddress": "Pune, Maharashtra"
}
```

## How to Run the Project

1. Clone the repository:

   `git clone https://github.com/Abolifaye13/customer-crud-gstin.git`

2. Open the project in VS Code.

3. Navigate to the Spring Boot project directory:

   `cd customer-crud`

4. Run the application:

   `.\mvnw.cmd spring-boot:run`

5. The application will start at:

   `http://localhost:8080`

## Run Tests

Execute the following command from the project directory:

`.\mvnw.cmd test`

The automated test suite covers CRUD operations, role-based authorization, and invalid GSTIN validation.

## H2 Database Console

URL: `http://localhost:8080/h2-console`

JDBC URL: `jdbc:h2:mem:customerdb`

Username: `sa`

Password: Leave blank.

## Author

**Aboli Faye**

GitHub: [Abolifaye13](https://github.com/Abolifaye13)
