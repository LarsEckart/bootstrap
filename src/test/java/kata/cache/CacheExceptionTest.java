package kata.cache;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CacheExceptionTest {
    @Nested
    class message_constructor {
        @Test
        void sets_message() throws Exception {
            var ex = new CacheException("error");

            assertThat(ex.getMessage()).isEqualTo("error");
        }
    }

    @Nested
    class message_and_cause_constructor {
        @Test
        void sets_message_and_cause() throws Exception {
            var cause = new RuntimeException("root");

            var ex = new CacheException("error", cause);

            assertThat(ex.getMessage()).isEqualTo("error");
            assertThat(ex.getCause()).isSameAs(cause);
        }
    }

    @Nested
    class cause_constructor {
        @Test
        void sets_cause() throws Exception {
            var cause = new RuntimeException("root");

            var ex = new CacheException(cause);

            assertThat(ex.getCause()).isSameAs(cause);
        }
    }
}
