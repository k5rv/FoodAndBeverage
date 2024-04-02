# Food and beverage services test framework

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
# Test Assignment Overview

This test assignment was developed under time constraints. Despite its completion, there are several areas where improvements can be made to better support testing needs. Below is a roadmap outlining features that should be implemented to enhance the framework:

## Roadmap

1. **Override Feign Exception Handling:** Modify the framework to override Feign's default exception handling behavior. Currently, Feign throws exceptions for all HTTP codes not in the 2XX range. By overriding this behavior, exceptions can be avoided, allowing for easier assertion of status codes.

2. **Wrap Feign Client:** Wrap the Feign client and utilize `ResponseEntity` to access status codes and other response attributes such as headers and body. This can be achieved by returning a custom object containing the restaurant object and response details.

3. **Consider RestAssured:** Explore the possibility of using RestAssured instead of Feign. However, note that RestAssured also requires wrapping to encapsulate implementation details.

4. **Separate API and UI Frameworks:** Implement a separation between API and UI frameworks. Package the API library separately and utilize it in UI tests for interactions with the backend.

5. **Utilize LoadableComponent for UI Tests:** Incorporate LoadableComponent and chains of page element initialization for UI tests. This approach can enhance the maintainability and reliability of UI test suites.

6. **Configure Capabilities and Test Setup for Different Browsers:** Allow for the configuration of capabilities and setup for tests in various browsers. This flexibility ensures comprehensive test coverage across different browser environments.

7. **Implement Parallel Test Execution with JUnit:** Configure JUnit to run tests in parallel. This optimization can significantly reduce test execution time, especially for large test suites.

8. **Integrate Allure Reporting:** Add Allure reporting to the framework. Allure provides comprehensive and visually appealing test reports, enhancing visibility and understanding of test results.


## Stack
- Spring Boot
- Spring Cloud OpenFeign
- Apache Maven
- JUnit 5
- Lombok
- Mapstruct
- Apache Commons
- Selenium
- WebDriver Manager