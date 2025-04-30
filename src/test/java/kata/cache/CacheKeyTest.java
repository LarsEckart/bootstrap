package kata.cache;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CacheKeyTest {
  @Nested
  class valid_construction {
    @Test
    void non_blank_string_creates_cache_key() {
      var key = new CacheKey("foo");

      assertThat(key.value()).isEqualTo("foo");
    }
  }

  @Nested
  class invalid_construction {
    @Test
    void null_string_throws_exception() {
      assertThatThrownBy(() -> new CacheKey(null))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("null or blank");
    }

    @Test
    void blank_string_throws_exception() {
      assertThatThrownBy(() -> new CacheKey("   "))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("null or blank");
    }
  }
}
