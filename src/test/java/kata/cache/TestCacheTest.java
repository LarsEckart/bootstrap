package kata.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TestCacheTest {
  @Nested
  class key_value_methods {
    @Test
    void put_and_get_returns_value() throws Exception {
      var cache = new TestCache();
      var key = new CacheKey("foo");
      var value = new CacheValue("bar");

      cache.put(key, value);
      var result = cache.get(key);

      assertThat(result).contains(value);
    }

    @Test
    void get_returns_empty_optional_when_key_missing() throws Exception {
      var cache = new TestCache();
      var key = new CacheKey("missing");

      var result = cache.get(key);

      assertThat(result).isEmpty();
    }

    @Test
    void getOrThrow_returns_value_when_present() throws Exception {
      var cache = new TestCache();
      var key = new CacheKey("foo");
      var value = new CacheValue("bar");

      cache.put(key, value);
      var result = cache.getOrThrow(key);

      assertThat(result).isEqualTo(value);
    }

    @Test
    void getOrThrow_throws_when_key_missing() {
      var cache = new TestCache();
      var key = new CacheKey("missing");

      assertThatThrownBy(() -> cache.getOrThrow(key))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("Key not found");
    }

    @Test
    void remove_deletes_key() throws Exception {
      var cache = new TestCache();
      var key = new CacheKey("foo");
      var value = new CacheValue("bar");

      cache.put(key, value);
      cache.remove(key);
      var result = cache.get(key);

      assertThat(result).isEmpty();
    }

    @Test
    void exists_returns_true_if_key_present() throws Exception {
      var cache = new TestCache();
      var key = new CacheKey("foo");
      var value = new CacheValue("bar");

      cache.put(key, value);
      var result = cache.exists(key);

      assertThat(result).isTrue();
    }

    @Test
    void exists_returns_false_if_key_missing() throws Exception {
      var cache = new TestCache();
      var key = new CacheKey("missing");

      var result = cache.exists(key);

      assertThat(result).isFalse();
    }
  }

  @Nested
  class hash_methods {
    @Test
    void hset_and_hget_returns_value() throws Exception {
      var cache = new TestCache();
      var hash = new HashName("h");
      var field = new HashField("f");
      var value = new CacheValue("v");

      cache.hset(hash, field, value);
      var result = cache.hget(hash, field);

      assertThat(result).contains(value);
    }

    @Test
    void hget_returns_empty_optional_when_hash_or_field_missing() throws Exception {
      var cache = new TestCache();
      var hash = new HashName("h");
      var field = new HashField("f");

      var result = cache.hget(hash, field);

      assertThat(result).isEmpty();
    }

    @Test
    void hgetOrThrow_returns_value_when_present() throws Exception {
      var cache = new TestCache();
      var hash = new HashName("h");
      var field = new HashField("f");
      var value = new CacheValue("v");

      cache.hset(hash, field, value);
      var result = cache.hgetOrThrow(hash, field);

      assertThat(result).isEqualTo(value);
    }

    @Test
    void hgetOrThrow_throws_when_hash_missing() {
      var cache = new TestCache();
      var hash = new HashName("missing_hash");
      var field = new HashField("missing_field");

      assertThatThrownBy(() -> cache.hgetOrThrow(hash, field))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("Field not found");
    }

    @Test
    void hgetOrThrow_throws_when_field_missing() {
      var cache = new TestCache();
      var hash = new HashName("h");
      var field = new HashField("f");
      cache.hset(hash, new HashField("other"), new CacheValue("v"));

      assertThatThrownBy(() -> cache.hgetOrThrow(hash, field))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("Field not found");
    }

    @Test
    void hdel_removes_field() throws Exception {
      var cache = new TestCache();
      var hash = new HashName("h");
      var field = new HashField("f");
      var value = new CacheValue("v");

      cache.hset(hash, field, value);
      cache.hdel(hash, field);
      var result = cache.hget(hash, field);

      assertThat(result).isEmpty();
    }
  }
}
