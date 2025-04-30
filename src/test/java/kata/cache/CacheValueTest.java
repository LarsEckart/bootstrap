package kata.cache;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CacheValueTest {
  @Nested
  class valid_construction {
    @Test
    void non_null_string_creates_cache_value() {
      var value = new CacheValue("bar");

      assertThat(value.value()).isEqualTo("bar");
    }
  }

  @Nested
  class invalid_construction {
    @Test
    void null_string_throws_exception() {
      assertThatThrownBy(() -> new CacheValue(null))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("must not be null");
    }
  }
}
