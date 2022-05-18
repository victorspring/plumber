Simple application for plumber accounting via REST API based on Spring Boot and Embedded PostgreSQL database. You don't have to run database separately, it will be automatically started along with the application and populated with test data using Liquibase. Note that data will be lost after the application stops.

For launching execute following command from the base directory of this project.

If you use Unix:
```
./mvnw spring-boot:run
```
If you use Windows:
```
.\mvnw.cmd spring-boot:run
```
---
For running unit and integration tests.

If you use Unix:
```
./mvnw test
```
If you use Windows:
```
.\mvnw.cmd test
```
Suggestions For Improvement:
- Security (OAuth 2.0 + OpenID + HTTPS)
- Pagination
- Restrictions on database level (Rules/Triggers)
- Hibernate validator
- Extend schema for storing addresses
- Use library for address validation (e.g. libaddressinput)
