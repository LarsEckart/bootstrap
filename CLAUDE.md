# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

### Building and Testing
- Build: `./gradlew build`
- Run tests: `./gradlew test`
- Run single test: `./gradlew test --tests "kata.AppTest.my_first_test"`
- Clean and rebuild: `./gradlew clean build`

### Code Quality
- Format code: `./gradlew spotlessApply`
- Run static analysis: `./gradlew spotbugsMain`
- Run mutation testing: `./gradlew pitest`
- Full quality check: `./gradlew check` (includes pitest)

## Architecture

This is a Java 21 project using Gradle with a focus on code quality and testing practices. The project structure follows standard Maven/Gradle conventions with main source in `src/main/java/kata/` and tests in `src/test/java/kata/`.

### Key Dependencies
- **Testing**: JUnit 5, AssertJ, Mockito, ApprovalTests, TCR extensions
- **Functional**: Vavr library for functional programming
- **Quality Tools**: Spotless (formatting), SpotBugs (static analysis), PiTest (mutation testing), ErrorProne

### Code Style Requirements
- Use Java 21 features (records, text blocks, pattern matching)
- NO comments in code - code should be self-documenting
- Use `var` when type names and variable names are identical
- Prefer classes over records except for DTOs
- Use `jakarta` namespace, not `javax`
- Test method names in snake_case following scenario_expectedOutcome pattern
- Structure tests with Arrange/Act/Assert separated by empty lines
- Use `@Nested` for test grouping instead of duplicating names
- Avoid null - use Optional, exceptions, or null objects instead
- Wrap primitives in Value Objects that validate constraints
- Follow Single Responsibility Principle