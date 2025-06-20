# 🛒 Order Management Service

This is a Spring Boot-based backend service for managing orders and products in an e-commerce system.

## 🚀 Features

- Create and manage orders
- Product stock validation with transactional rollback
- RESTful API design
- JPA/Hibernate integration
- Custom exception handling
- Database schema initialization using `import.sql`
- Unit and integration testing with JUnit

---

## 📦 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 / PostgreSQL / MySQL (configurable)
- Maven / Gradle
- JUnit 5
- Lombok
- MapStruct (if used)

---

## ⚙️ Getting Started

### Prerequisites

- Java 17+
- Maven or Gradle
- (Optional) Docker (for DB or deployment)

### Clone the Repository

```bash
git clone https://github.com/vignesh-gitsource/inventory-management-repo.git

**### Access database**
  - http://localhost:8088/h2-console
  - Use the jdbc url : spring.datasource.url=jdbc:h2:mem:inventorydb;

**### Project Structure**
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
│       │   └── dto/
│       └── test/java/com/cams/inventory/management/api/
│           └── controller/
│
├── app/                           # Main application launcher (Spring Boot entry point)
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/cams/inventory/management/app/
│       │   └── InventoryManagementApplication.java
│       └── test/java/com/cams/inventory/management/app/
│
├── client/                        # HTTP/Feign clients (e.g., for external services)
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/cams/inventory/management/client/
│       └── test/java/com/cams/inventory/management/client/
│
├── db/                            # Database schema, migration, initial data
│   ├── pom.xml
│   └── src/
│       └── main/resources/
│           ├── schema.sql
│           ├── import.sql
│
├── domain/                        # Business logic, services, entities, repositories
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/cams/inventory/management/domain/
│       │   ├── model/             # JPA entities
│       │   ├── repository/        # Spring Data JPA repositories
│       │   ├── service/           # Business services
│       │   └── mapper/            # Optional: MapStruct mappers
│       └── test/java/com/cams/inventory/management/domain/
│           ├── service/
│           └── repository/
│
├── exception/                     # Common exception classes and handlers
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/cams/inventory/management/exception/
│       │   ├── InsufficientStockException.java
│       │   ├── ResourceNotFoundException.java
│       │   └── GlobalExceptionHandler.java
│       └── test/java/com/cams/inventory/management/exception/

**### Access endpoints using swagger**
  -http://localhost:8088/api/swagger-ui/index.html

