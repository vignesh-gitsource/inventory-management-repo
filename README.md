# Inventory Management Service

This is a Spring Boot-based backend service for managing inventory for orders and products in an e-commerce system.

## Features

- Create and manage orders and products
- Product stock validation with transactional rollback
- RESTful API design
- JPA/Hibernate integration
- Custom exception handling
- Database schema initialization using `import.sql`
- Unit JUnit

---

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 / PostgreSQL / MySQL (configurable)
- Maven
- JUnit 5
- Lombok
- MapStruct (if used)

---

## Getting Started

### Prerequisites

- Java 17+
- Maven

### Clone the Repository
- git clone https://github.com/vignesh-gitsource/inventory-management-repo.git

### Access database
- http://localhost:8088/h2-console
- Use the jdbc url : spring.datasource.url=jdbc:h2:mem:inventorydb;


### Project Structure
<pre>
inventory-management-system
│
├── pom.xml                        # Root POM for multi-module build
├── README.md
│
├── api/                           # REST API layer
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/cams/inventory/management/api/
│       │   ├── controller/
│       └── test/java/com/cams/inventory/management/api/
│           └── controller/
│
├── app/                           # Main application launcher (Spring Boot entry point)
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/cams/inventory/management/app/
│       │   │   └── InventoryManagementApplication.java
│       │   └── resources/
│       │       ├── application.properties
│       │       └── import.sql
│       └── test/java/com/cams/inventory/management/app/
│          
│
├── client/                        # HTTP/Feign clients (e.g., for external services)
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/cams/inventory/management/client/
│       │   └── request/
│       └── test/java/com/cams/inventory/management/client/
│
├── db/                           # Database schema, migration, initial data
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/cams/inventory/management/db/
│       │   │   ├── dao/
│       │   │   ├── dto/
│       │   │   ├── entity/
│       │   │   ├── mapper/
│       │   │   └── repository/
│       └── test/
│           └── java/com/cams/inventory/management/db/
│               ├── dao/
│
├── domain/                       # Business logic, services, entities, repositories
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/cams/inventory/management/domain/
│       │   ├── service/           # Business services
│       └── test/java/com/cams/inventory/management/domain/
│           ├── service/
│
├── exception/                   # Common exception classes and handlers
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/cams/inventory/management/exception/
│       │   ├── handler/
│       │   ├── response/
│       └── test/java/com/cams/inventory/management/exception/


</pre>
### Access endpoints using swagger
-http://localhost:8088/api/swagger-ui/index.html

```bash
  
