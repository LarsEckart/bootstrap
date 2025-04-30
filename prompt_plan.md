# Step-by-Step Blueprint and Prompts for Cache Abstraction Project

## High-Level Blueprint

1. Define value objects: CacheKey, CacheValue, HashName, HashField.
2. Define the CacheException.
3. Define the Cache interface with required methods.
4. Implement and test the TestCache (ConcurrentHashMap-backed).
5. Implement and test the RedisCache (non-SSL).
6. Implement and test the RedisSslCache (SSL).
7. Create the contract test (abstract test class) for Cache.
8. Implement contract test subclasses for TestCache and RedisCache.
9. Integrate Testcontainers for Redis-backed tests.
10. Final integration and wiring.

---

## Iterative Chunks and Steps

### Chunk 1: Value Objects
- Step 1.1: Implement CacheKey (with tests)
- Step 1.2: Implement CacheValue (with tests)
- Step 1.3: Implement HashName (with tests)
- Step 1.4: Implement HashField (with tests)

### Chunk 2: Exception
- Step 2.1: Implement CacheException (with tests)

### Chunk 3: Cache Interface
- Step 3.1: Define Cache Interface

### Chunk 4: TestCache Implementation
- Step 4.1: Implement TestCache
- Step 4.2: TestCache Unit Tests

### Chunk 5: RedisCache Implementation
- Step 5.1: Implement RedisCache (non-SSL)
- Step 5.2: RedisCache Unit Tests

### Chunk 6: RedisSslCache Implementation
- Step 6.1: Implement RedisSslCache (SSL)
- Step 6.2: RedisSslCache Unit Tests

### Chunk 7: Contract Test
- Step 7.1: Create Abstract Contract Test

### Chunk 8: Contract Test Subclasses
- Step 8.1: TestCache Contract Test
- Step 8.2: RedisCache Contract Test

### Chunk 9: Testcontainers Integration
- Step 9.1: Integrate Testcontainers

### Chunk 10: Final Integration
- Step 10.1: Review and Wire Up

---

## Prompts for Code-Generation LLM

### Prompt 1.1: Implement CacheKey
```
Implement the CacheKey class in the kata.cache package. It should wrap a String, validate that the value is non-null and non-blank, and provide a value() accessor. Write unit tests covering valid and invalid construction, using JUnit 5 and the provided testing conventions.
```

### Prompt 1.2: Implement CacheValue
```
Implement the CacheValue class in the kata.cache package. It should wrap a String, validate that the value is non-null, and provide a value() accessor. Write unit tests covering valid and invalid construction, using JUnit 5 and the provided testing conventions.
```

### Prompt 1.3: Implement HashName
```
Implement the HashName class in the kata.cache package. It should wrap a String, validate that the value is non-null and non-blank, and provide a value() accessor. Write unit tests covering valid and invalid construction, using JUnit 5 and the provided testing conventions.
```

### Prompt 1.4: Implement HashField
```
Implement the HashField class in the kata.cache package. It should wrap a String, validate that the value is non-null and non-blank, and provide a value() accessor. Write unit tests covering valid and invalid construction, using JUnit 5 and the provided testing conventions.
```

### Prompt 2.1: Implement CacheException
```
Implement the CacheException class in the kata.cache package as a checked exception. It should have constructors for message and cause. Write unit tests for its construction and message.
```

### Prompt 3.1: Define Cache Interface
```
Define the Cache interface in the kata.cache package. It should be fixed to Cache<String, String> and provide synchronous methods for get, put, remove, exists, hget, hset, hdel. For get/hget, provide both Optional-returning and throwing variants. All methods should use the value objects for parameters and throw CacheException for error scenarios.
```

### Prompt 4.1: Implement TestCache
```
Implement the TestCache class in the kata.cache package. It should implement the Cache interface using ConcurrentHashMap for storage. All methods must use value objects and handle input validation. Do not use nulls. 
```

### Prompt 4.2: TestCache Unit Tests
```
Write unit tests for TestCache, covering all methods, happy paths, negative flows, and input validation. Use JUnit 5, @Nested for grouping, and snake_case for test names. Follow Arrange-Act-Assert structure.
```

### Prompt 5.1: Implement RedisCache (non-SSL)
```
Implement the RedisCache class in the kata.cache package. It should implement the Cache interface using Lettuce and Apache Commons Pool. Constructor parameters should configure host, port, pool size, and timeouts. All methods must use value objects and throw CacheException for errors.
```

### Prompt 5.2: RedisCache Unit Tests
```
Write unit tests for RedisCache construction and error handling. Use JUnit 5 and the provided conventions.
```

### Prompt 6.1: Implement RedisSslCache (SSL)
```
Implement the RedisSslCache class in the kata.cache package. It should implement the Cache interface using Lettuce and Apache Commons Pool, with SSL enabled. Constructor parameters should configure host, port, pool size, timeouts, and SSL. All methods must use value objects and throw CacheException for errors.
```

### Prompt 6.2: RedisSslCache Unit Tests
```
Write unit tests for RedisSslCache construction and error handling. Use JUnit 5 and the provided conventions.
```

### Prompt 7.1: Create Abstract Contract Test
```
Implement an abstract contract test class for Cache in the kata.cache package. It should cover all required behaviors and edge cases for the Cache interface, using JUnit 5, @Nested, and snake_case test names. Follow Arrange-Act-Assert structure.
```

### Prompt 8.1: TestCache Contract Test
```
Implement a contract test subclass for TestCache, extending the abstract contract test class. Ensure all contract tests pass for TestCache.
```

### Prompt 8.2: RedisCache Contract Test
```
Implement a contract test subclass for RedisCache, extending the abstract contract test class. Use Testcontainers to provide a real Redis instance. Ensure all contract tests pass for RedisCache.
```

### Prompt 9.1: Integrate Testcontainers
```
Integrate Testcontainers for Redis-backed tests. Ensure the RedisCache contract tests run against a real Redis instance spun up by Testcontainers.
```

### Prompt 10.1: Review and Wire Up
```
Review all code, ensure all tests pass, and wire up all components. Ensure there is no orphaned or unused code.
```
