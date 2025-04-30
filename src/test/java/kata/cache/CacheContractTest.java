package kata.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

abstract class CacheContractTest {
  protected abstract Cache cache();

  @Nested
  class key_value_contract {
    @Test
    void put_and_get_returns_value() throws Exception {
      var key = new CacheKey("key1");
      var value = new CacheValue("value1");

      cache().put(key, value);
      var result = cache().get(key);

      assertThat(result).contains(value);
    }

    @Test
    void get_returns_empty_optional_when_key_missing() throws Exception {
      var key = new CacheKey("missing_key");

      var result = cache().get(key);

      assertThat(result).isEmpty();
    }

    @Test
    void getOrThrow_returns_value_when_present() throws Exception {
      var key = new CacheKey("key2");
      var value = new CacheValue("value2");

      cache().put(key, value);
      var result = cache().getOrThrow(key);

      assertThat(result).isEqualTo(value);
    }

    @Test
    void getOrThrow_throws_when_key_missing() {
      var key = new CacheKey("missing_key2");

      assertThatThrownBy(() -> cache().getOrThrow(key))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("Key not found");
    }

    @Test
    void remove_deletes_key() throws Exception {
      var key = new CacheKey("key3");
      var value = new CacheValue("value3");

      cache().put(key, value);
      cache().remove(key);
      var result = cache().get(key);

      assertThat(result).isEmpty();
    }

    @Test
    void exists_returns_true_if_key_present() throws Exception {
      var key = new CacheKey("key4");
      var value = new CacheValue("value4");

      cache().put(key, value);
      var result = cache().exists(key);

      assertThat(result).isTrue();
    }

    @Test
    void exists_returns_false_if_key_missing() throws Exception {
      var key = new CacheKey("missing_key3");

      var result = cache().exists(key);

      assertThat(result).isFalse();
    }
  }

  @Nested
  class hash_contract {
    @Test
    void hset_and_hget_returns_value() throws Exception {
      var hash = new HashName("hash1");
      var field = new HashField("field1");
      var value = new CacheValue("hash_value1");

      cache().hset(hash, field, value);
      var result = cache().hget(hash, field);

      assertThat(result).contains(value);
    }

    @Test
    void hget_returns_empty_optional_when_hash_or_field_missing() throws Exception {
      var hash = new HashName("missing_hash");
      var field = new HashField("missing_field");

      var result = cache().hget(hash, field);

      assertThat(result).isEmpty();
    }

    @Test
    void hgetOrThrow_returns_value_when_present() throws Exception {
      var hash = new HashName("hash2");
      var field = new HashField("field2");
      var value = new CacheValue("hash_value2");

      cache().hset(hash, field, value);
      var result = cache().hgetOrThrow(hash, field);

      assertThat(result).isEqualTo(value);
    }

    @Test
    void hgetOrThrow_throws_when_hash_missing() {
      var hash = new HashName("missing_hash2");
      var field = new HashField("missing_field2");

      assertThatThrownBy(() -> cache().hgetOrThrow(hash, field))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("Field not found");
    }

    @Test
    void hgetOrThrow_throws_when_field_missing() throws Exception {
      var hash = new HashName("hash3");
      var field = new HashField("missing_field3");
      cache().hset(hash, new HashField("other_field"), new CacheValue("hash_value3"));

      assertThatThrownBy(() -> cache().hgetOrThrow(hash, field))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("Field not found");
    }

    @Test
    void hdel_removes_field() throws Exception {
      var hash = new HashName("hash4");
      var field = new HashField("field4");
      var value = new CacheValue("hash_value4");

      cache().hset(hash, field, value);
      cache().hdel(hash, field);
      var result = cache().hget(hash, field);

      assertThat(result).isEmpty();
    }
  }
}
