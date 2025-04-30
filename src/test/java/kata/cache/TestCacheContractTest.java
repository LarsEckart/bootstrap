package kata.cache;

import org.junit.jupiter.api.BeforeEach;

class TestCacheContractTest extends CacheContractTest {
  private Cache testCache;

  @BeforeEach
  void setUp() {
    testCache = new TestCache();
  }

  @Override
  protected Cache cache() {
    return testCache;
  }
}
