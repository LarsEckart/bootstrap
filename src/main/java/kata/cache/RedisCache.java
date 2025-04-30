package kata.cache;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import java.time.Duration;
import java.util.Optional;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class RedisCache implements Cache {
  private final GenericObjectPool<StatefulRedisConnection<String, String>> pool;
  private final RedisClient client;

  public RedisCache(String host, int port, int poolSize, Duration timeout) {
    var redisUri = RedisURI.builder().withHost(host).withPort(port).withTimeout(timeout).build();
    this.client = RedisClient.create(redisUri);
    var factory = new RedisConnectionFactory(client);
    var config = new GenericObjectPoolConfig<StatefulRedisConnection<String, String>>();
    config.setMaxTotal(poolSize);
    this.pool = new GenericObjectPool<>(factory, config);
  }

  public void close() {
    pool.close();
    client.shutdown();
  }

  @Override
  public Optional<CacheValue> get(CacheKey key) throws CacheException {
    StatefulRedisConnection<String, String> connection = null;
    try {
      connection = pool.borrowObject();
      var value = connection.sync().get(key.value());
      return Optional.ofNullable(value).map(CacheValue::new);
    } catch (Exception e) {
      throw new CacheException("Failed to get value", e);
    } finally {
      if (connection != null) {
        pool.returnObject(connection);
      }
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
    StatefulRedisConnection<String, String> connection = null;
    try {
      connection = pool.borrowObject();
      connection.sync().set(key.value(), value.value());
    } catch (Exception e) {
      throw new CacheException("Failed to put value", e);
    } finally {
      if (connection != null) {
        pool.returnObject(connection);
      }
    }
  }

  @Override
  public void remove(CacheKey key) throws CacheException {
    StatefulRedisConnection<String, String> connection = null;
    try {
      connection = pool.borrowObject();
      connection.sync().del(key.value());
    } catch (Exception e) {
      throw new CacheException("Failed to remove value", e);
    } finally {
      if (connection != null) {
        pool.returnObject(connection);
      }
    }
  }

  @Override
  public boolean exists(CacheKey key) throws CacheException {
    StatefulRedisConnection<String, String> connection = null;
    try {
      connection = pool.borrowObject();
      return connection.sync().exists(key.value()) > 0;
    } catch (Exception e) {
      throw new CacheException("Failed to check existence", e);
    } finally {
      if (connection != null) {
        pool.returnObject(connection);
      }
    }
  }

  @Override
  public Optional<CacheValue> hget(HashName hash, HashField field) throws CacheException {
    StatefulRedisConnection<String, String> connection = null;
    try {
      connection = pool.borrowObject();
      var value = connection.sync().hget(hash.value(), field.value());
      return Optional.ofNullable(value).map(CacheValue::new);
    } catch (Exception e) {
      throw new CacheException("Failed to hget value", e);
    } finally {
      if (connection != null) {
        pool.returnObject(connection);
      }
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
    StatefulRedisConnection<String, String> connection = null;
    try {
      connection = pool.borrowObject();
      connection.sync().hset(hash.value(), field.value(), value.value());
    } catch (Exception e) {
      throw new CacheException("Failed to hset value", e);
    } finally {
      if (connection != null) {
        pool.returnObject(connection);
      }
    }
  }

  @Override
  public void hdel(HashName hash, HashField field) throws CacheException {
    StatefulRedisConnection<String, String> connection = null;
    try {
      connection = pool.borrowObject();
      connection.sync().hdel(hash.value(), field.value());
    } catch (Exception e) {
      throw new CacheException("Failed to hdel value", e);
    } finally {
      if (connection != null) {
        pool.returnObject(connection);
      }
    }
  }

  private static class RedisConnectionFactory
      extends BasePooledObjectFactory<StatefulRedisConnection<String, String>> {
    private final RedisClient client;

    RedisConnectionFactory(RedisClient client) {
      this.client = client;
    }

    @Override
    public StatefulRedisConnection<String, String> create() {
      return client.connect();
    }

    @Override
    public PooledObject<StatefulRedisConnection<String, String>> wrap(
        StatefulRedisConnection<String, String> conn) {
      return new DefaultPooledObject<>(conn);
    }

    @Override
    public void destroyObject(PooledObject<StatefulRedisConnection<String, String>> p) {
      p.getObject().close();
    }
  }
}
