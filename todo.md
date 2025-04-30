# TODO Checklist for Cache Abstraction Project

## Value Objects
- [x] Implement `CacheKey` class with validation
- [x] Write unit tests for `CacheKey` (valid and invalid construction)
- [x] Implement `CacheValue` class with validation
- [x] Write unit tests for `CacheValue` (valid and invalid construction)
- [x] Implement `HashName` class with validation
- [x] Write unit tests for `HashName` (valid and invalid construction)
- [x] Implement `HashField` class with validation
- [x] Write unit tests for `HashField` (valid and invalid construction)

## Exception
- [x] Implement `CacheException` (checked exception)
- [x] Write unit tests for `CacheException` construction and message

## Cache Interface
- [x] Define `Cache` interface with all required methods (get, put, remove, exists, hget, hset, hdel, Optional and throwing variants)

## TestCache Implementation
- [ ] Implement `TestCache` using `ConcurrentHashMap`
- [ ] Write unit tests for `TestCache` (all methods, happy and negative paths, input validation)

## RedisCache Implementation (non-SSL)
- [ ] Implement `RedisCache` with Lettuce and Apache Commons Pool (host, port, pool size, timeouts)
- [ ] Write unit tests for `RedisCache` construction and error handling

## RedisSslCache Implementation (SSL)
- [ ] Implement `RedisSslCache` with Lettuce and Apache Commons Pool (host, port, pool size, timeouts, SSL)
- [ ] Write unit tests for `RedisSslCache` construction and error handling

## Contract Testing
- [ ] Create abstract contract test class for `Cache` (all required behaviors and edge cases)
- [ ] Implement contract test subclass for `TestCache`
- [ ] Implement contract test subclass for `RedisCache` (using Testcontainers)

## Testcontainers Integration
- [ ] Integrate Testcontainers for Redis-backed tests
- [ ] Ensure RedisCache contract tests run against a real Redis instance

## Final Integration
- [ ] Review all code and ensure all tests pass
- [ ] Wire up all components
- [ ] Ensure no orphaned or unused code

## Gradle & Tooling
- [ ] Ensure Gradle build uses `[TODO: lookup latest]` for dependency versions
- [ ] Use Java 21 features throughout
- [ ] Use `jakarta` namespace, not `javax`

## Coding Standards
- [ ] Avoid nulls (use Optional, exceptions, or null objects)
- [ ] Favour value objects and first-class collections
- [ ] Follow SRP and keep components small and focused
- [ ] No comments in code or tests
- [ ] All logic and calculations must be covered by tests
- [ ] Use @Nested for test grouping, snake_case for test names, and Arrange-Act-Assert structure
