package kata.cache;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HashFieldTest {
  @Nested
  class valid_construction {
    @Test
    void non_blank_string_creates_hash_field() {
      var field = new HashField("field1");

      assertThat(field.value()).isEqualTo("field1");
    }
  }

  @Nested
  class invalid_construction {
    @Test
    void null_string_throws_exception() {
      assertThatThrownBy(() -> new HashField(null))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("null or blank");
    }

    @Test
    void blank_string_throws_exception() {
      assertThatThrownBy(() -> new HashField("   "))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("null or blank");
    }
  }
}
