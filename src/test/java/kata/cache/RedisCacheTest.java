package kata.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RedisCacheTest {
  @Nested
  class construction {
    @Test
    void valid_parameters_create_instance() {
      var cache = new RedisCache("localhost", 6379, 2, Duration.ofSeconds(1));
      assertThat(cache).isNotNull();
    }
  }

  @Nested
  class error_handling {
    @Test
    void get_throws_cache_exception_on_connection_failure() {
      var cache = new RedisCache("invalid-host", 1234, 1, Duration.ofMillis(100));
      var key = new CacheKey("foo");

      assertThatThrownBy(() -> cache.get(key))
          .isInstanceOf(CacheException.class)
          .hasMessageContaining("Failed to get value");
    }

    @Test
    void put_throws_cache_exception_on_connection_failure() {
      var cache = new RedisCache("invalid-host", 1234, 1, Duration.ofMillis(100));
      var key = new CacheKey("foo");
      var value = new CacheValue("bar");

      assertThatThrownBy(() -> cache.put(key, value))
          .isInstanceOf(CacheException.class)
          .hasMessageContaining("Failed to put value");
    }
  }
}
