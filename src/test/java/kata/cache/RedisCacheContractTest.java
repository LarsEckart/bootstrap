package kata.cache;

import com.redis.testcontainers.RedisContainer;
import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class RedisCacheContractTest extends CacheContractTest {
  RedisContainer redis;
  RedisCache cache;

  @BeforeAll
  void startRedis() {
    redis = new RedisContainer(RedisContainer.DEFAULT_IMAGE_NAME.withTag("7.4.2"));
    redis.start();
    cache = new RedisCache(redis.getHost(), redis.getFirstMappedPort(), 2, Duration.ofSeconds(2));
  }

  @AfterAll
  void stopRedis() {
    if (cache != null) cache.close();
    if (redis != null) redis.stop();
  }

  @Override
  protected Cache cache() {
    return cache;
  }
}
