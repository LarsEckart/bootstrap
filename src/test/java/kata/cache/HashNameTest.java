package kata.cache;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class HashNameTest {
    @Nested
    class valid_construction {
        @Test
        void non_blank_string_creates_hash_name() {
            var name = new HashName("hash1");

            assertThat(name.value()).isEqualTo("hash1");
        }
    }

    @Nested
    class invalid_construction {
        @Test
        void null_string_throws_exception() {
            assertThatThrownBy(() -> new HashName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null or blank");
        }

        @Test
        void blank_string_throws_exception() {
            assertThatThrownBy(() -> new HashName("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null or blank");
        }
    }
}
