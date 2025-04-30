package kata.cache;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import java.time.Duration;
import java.util.Optional;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class RedisCache implements Cache {
  private final GenericObjectPool<StatefulRedisConnection<String, String>> pool;
  private final RedisClient client;

  public RedisCache(String host, int port, int poolSize, Duration timeout) {
    var redisUri = RedisURI.builder().withHost(host).withPort(port).withTimeout(timeout).build();
    this.client = RedisClient.create(redisUri);
    var config = new GenericObjectPoolConfig<StatefulRedisConnection<String, String>>();
    config.setMaxTotal(poolSize);
    this.pool = ConnectionPoolSupport.createGenericObjectPool(() -> client.connect(), config);
  }

  public void close() {
    pool.close();
    client.shutdown();
  }

  @Override
  public Optional<CacheValue> get(CacheKey key) throws CacheException {
    try (var connection = pool.borrowObject()) {
      var value = connection.sync().get(key.value());
      return Optional.ofNullable(value).map(CacheValue::new);
    } catch (Exception e) {
      throw new CacheException("Failed to get value", e);
    }
  }

  @Override
  public CacheValue getOrThrow(CacheKey key) throws CacheException {
    var value = get(key);
    if (value.isEmpty()) throw new RuntimeException("Key not found: " + key.value());
    return value.get();
  }

  @Override
  public void put(CacheKey key, CacheValue value) throws CacheException {
    try (var connection = pool.borrowObject()) {
      connection.sync().set(key.value(), value.value());
    } catch (Exception e) {
      throw new CacheException("Failed to put value", e);
    }
  }

  @Override
  public void remove(CacheKey key) throws CacheException {
    try (var connection = pool.borrowObject()) {
      connection.sync().del(key.value());
    } catch (Exception e) {
      throw new CacheException("Failed to remove value", e);
    }
  }

  @Override
  public boolean exists(CacheKey key) throws CacheException {
    try (var connection = pool.borrowObject()) {
      return connection.sync().exists(key.value()) > 0;
    } catch (Exception e) {
      throw new CacheException("Failed to check existence", e);
    }
  }

  @Override
  public Optional<CacheValue> hget(HashName hash, HashField field) throws CacheException {
    try (var connection = pool.borrowObject()) {
      var value = connection.sync().hget(hash.value(), field.value());
      return Optional.ofNullable(value).map(CacheValue::new);
    } catch (Exception e) {
      throw new CacheException("Failed to hget value", e);
    }
  }

  @Override
  public CacheValue hgetOrThrow(HashName hash, HashField field) throws CacheException {
    var value = hget(hash, field);
    if (value.isEmpty()) throw new RuntimeException("Field not found: " + field.value());
    return value.get();
  }

  @Override
  public void hset(HashName hash, HashField field, CacheValue value) throws CacheException {
    try (var connection = pool.borrowObject()) {
      connection.sync().hset(hash.value(), field.value(), value.value());
    } catch (Exception e) {
      throw new CacheException("Failed to hset value", e);
    }
  }

  @Override
  public void hdel(HashName hash, HashField field) throws CacheException {
    try (var connection = pool.borrowObject()) {
      connection.sync().hdel(hash.value(), field.value());
    } catch (Exception e) {
      throw new CacheException("Failed to hdel value", e);
    }
  }
}
