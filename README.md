# Food and beverages services test framework

## Description
This project is a Test Assignment. It includes REST API and UI tests.

## Prerequisites
- Java 17 or higher
- Maven 3.9.*

## Instructions
To run the REST API tests, navigate to the project directory and execute the following commands:
```bash
mvn '-Dtest=com.foodandbeverage.api.*Test' test
```

To run the UI tests, navigate to the project directory and execute the following commands:
```bash
mvn '-Dtest=com.foodandbeverage.ui.*Test' test
```

## Stack
- Spring Boot
- Spring Cloud OpenFeign
- JUnit 5
- Lombok
- Mapstruct
- commons-lang3
- Selenium
- WebDriver Manager