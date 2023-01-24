# Technical Test for Inditex

This repository contains a technical test for Inditex company

## Project Structure

The app name is **inditex**

The base package is **com.iherrero**, due to the author of this project

The project structure is the following one:

- **/src/main** folder: contains the code implementation
    - **/config** folder: for general config (Jackson, Logger, Swagger)
    - **/utils** folder: for utility functions
    - **/prices** folder: is the base folder for the bounded context 'prices'. The main subfolders are:
        - **/domain** folder: contains the domain layer (entity, errors, service)
        - **/inbound** folder: contains the controllers and DTOs
        - **/outbound** folder: contains the repository and entities
- **/src/test** folder: contains the tests
    - **/utils** folder: for utility functions
    - **/prices** folder: is the base folder for the tests of the bounded context 'prices'

## Project Build

Gradle with Kotlin DSL is used to declare and configure the dependencies, and
then gradle as building tool for our project:

    ./gradlew build

## Project Run

### Local

As this app is developed by using the SpringBoot framework, all is needed is executing the following command:

    ./gradlew bootRun

## Testing

### H2 Database

This project works over an in-memory database (H2). The access URL is the following one:

http://localhost:8080/h2-console/login.do

The data access can be found in the following file:

    /src/main/resources/application.yaml

### Locally with Newman

You can install newman (a postman version to run from command-line) that you can get with brew:

    brew install newman

or

    npm install -g newman

Once installed, you can run the set of tests against the selected environment:

    newman run ./src/test/resources/postman/prices-API-postman_collection.json \-e ./src/test/resources/postman/prices-local-postman_environment.json

NOTE: be aware to use the proper environment (flag `-e`) to launch tests to the right location

### Swagger

The app can also be tested manually by accessing the following URL:

http://localhost:8080/swagger-ui/index.html

All the API documentation can be checked here:

http://localhost:8080/v2/api-docs
