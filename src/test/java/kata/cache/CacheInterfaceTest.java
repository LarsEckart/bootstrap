package kata.cache;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

class CacheInterfaceTest {
    @Nested
    class method_signatures {
        @Test
        void cache_interface_methods_have_expected_signatures() {
            var methods = Cache.class.getDeclaredMethods();
            assertThat(methods)
                .extracting(m -> m.getName())
                .containsExactlyInAnyOrder(
                    "get", "getOrThrow", "put", "remove", "exists",
                    "hget", "hgetOrThrow", "hset", "hdel"
                );
        }
    }
}
