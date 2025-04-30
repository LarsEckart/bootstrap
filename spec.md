# Cache Abstraction Specification

## Overview
This project provides a facade abstraction for Redis, using the Lettuce library and Apache Commons Pool. The goal is to decouple application code from direct Redis usage, enabling easy swapping of the backend (e.g., for testing with an in-memory implementation).

## Package Structure
- All code will reside under the `kata.cache` package.

## Interface
- The main abstraction is `Cache`, fixed to `Cache<String, String>`.
- Synchronous API only.
- Supported operations:
  - Key-Value: `get`, `put`, `remove`, `exists`
  - Hash (single-field): `hget`, `hset`, `hdel`
- For all `get`/`hget` operations:
  - One variant returns `Optional<String>`
  - One variant throws a runtime exception if not found
- All error scenarios (e.g., Redis unavailable, connection issues, invalid input) throw a single custom checked exception: `CacheException`.

## Value Objects
- All primitives and strings are wrapped in value objects:
  - `CacheKey`, `CacheValue`, `HashName`, `HashField`
- Each value object validates its input (e.g., non-null, non-blank, sensible length/character constraints).

## Implementations
- `RedisCache` (non-SSL) and `RedisSslCache` (SSL) using Lettuce and Apache Commons Pool.
  - Constructor parameters configure host, port, pool size, timeouts, and SSL (for `RedisSslCache`).
- `TestCache` using `ConcurrentHashMap` (thread-safe, but single-threaded use is expected).

## Testing
- All logic and calculations must be covered by tests, including negative flows and input validation.
- Tests use JUnit 5 and Testcontainers to spin up a real Redis instance for integration tests.
- A contract test (abstract test class) defines the expected `Cache` behavior.
  - Subclasses provide the Redis-backed and TestCache implementations.
- Tests use @Nested for grouping, snake_case for test names, and follow Arrange-Act-Assert structure.
- No comments in code or tests.

## Java & Tooling
- Java 21 features are used throughout.
- Gradle is the build tool.
- Use the `jakarta` namespace, not `javax`.
- Avoid nulls: use Optional, exceptions, or null objects as appropriate.
- Favour value objects and first-class collections.
- Follow SRP and keep components small and focused.

## TODO
- [TODO: lookup latest] for dependency versions in Gradle.
- If not all logic is implemented, use // TODO: comments and indicate where to continue.
