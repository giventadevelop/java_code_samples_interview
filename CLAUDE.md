# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.2.0 Java application serving as a collection of interview coding samples and algorithm implementations. The project uses Maven as its build tool and Java 17 as the target version.

## Build and Development Commands

### Maven Commands
- `mvn clean compile` - Clean and compile the project
- `mvn test` - Run all JUnit tests
- `mvn spring-boot:run` - Run the Spring Boot application (starts on port 8081)
- `mvn package` - Build the JAR file
- `mvn clean package` - Clean build and create JAR

### Running the Application
- The Spring Boot application starts on port 8081 (configured in application.properties)
- REST endpoint available at: `http://localhost:8081/api/hello`
- Main class: `com.boot2.Boot2Application`

### Testing
- Uses JUnit 5 (jupiter) for testing
- Test classes in `src/test/java/com/boot2/`
- Run specific test: `mvn test -Dtest=ClassName`

## Code Architecture

### Package Structure
- All classes are in the `com.boot2` package
- Main application: `Boot2Application.java` (Spring Boot entry point)
- REST controller: `BootSampleController.java` (provides `/api/hello` endpoint)

### Code Sample Categories
The repository contains various standalone Java classes demonstrating:

**Algorithm Implementations:**
- `AnagramChecker.java` - String anagram detection using Java 8 streams
- `BracketMatcher.java`, `ClosingBrackets.java` - Bracket matching algorithms
- `LongestSubString.java` / `LongestSubStringStringBuilder.java` - longest substring without repeating characters (sliding window / StringBuilder)
- `PrimeOrNot.java` - Prime number checking
- `HikingCalculator.java` - Path calculation algorithms

**Data Structure Operations:**
- `SortHashMapByKeys.java`, `SortHashMapByValues.java` - HashMap sorting
- `CharacterOccurrenceInArray.java` - Array processing
- `CountOfWords.java` - Text processing

**Utility Classes:**
- `NumberCommaSeparator.java` - Number formatting
- `NumbersDivisibleBy3And5.java`, `NumbersDivisibleFizzBuzz.java` - Mathematical operations
- `CheckNumberEvenOrOdd.java` - Basic number operations

**Business Logic Examples:**
- `VendingMachine.java` - Command-line based vending machine simulation
- `CheckWarehouseStock.java` - Inventory management example
- `Payment.java`, `Employee.java` - Business domain models

**Concurrency Examples:**
- `Thread1.java`, `Thread2.java` - Basic threading
- `ThreadProducer.java`, `ThreadConsumer.java` - Producer-consumer pattern
- `ThreadLock.java`, `ThreadGroup.java` - Advanced threading concepts

**File Processing:**
- `ExcelCreator.java` - Apache POI Excel file creation
- `WordProcess.java` - Text file processing

### Dependencies
- Spring Boot Web Starter (REST endpoints)
- Spring Boot DevTools (development hot reload)
- Lombok (boilerplate code reduction)
- Apache POI (Excel file processing)
- Apache Commons Collections (utility collections)

## Running Individual Code Samples

Most classes contain `main` methods and can be executed directly:
- Compile: `javac -cp "target/classes" src/main/java/com/boot2/ClassName.java`
- Run: `java -cp "target/classes" com.boot2.ClassName [args]`

For classes requiring command-line arguments (like `VendingMachine.java`, `HikingCalculator.java`), check the `main` method for expected parameter format.

## Testing Approach

- Unit tests use JUnit 5
- Spring Boot test context available via `@SpringBootTest`
- Integration tests can test the REST endpoints using Spring's test framework
- Individual algorithm classes can be tested with standard unit tests