package kata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Request;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.kv.model.PutParams;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.consul.ConsulContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class ConsulUtilsAndEdgeCasesTest {

  @Container static final ConsulContainer consul = TestConsulContainers.newContainer();

  private ConsulClient consulClient;
  private ConsulRawClient rawClient;

  @BeforeEach
  void setUp() {
    consulClient = new ConsulClient(consul.getHost(), consul.getMappedPort(8500));
    rawClient = new ConsulRawClient.Builder(consul.getHost(), consul.getMappedPort(8500)).build();
  }

  @Test
  void testPutParamsAllOptions() {
    PutParams params = new PutParams();
    params.setFlags(42L);
    params.setCas(123L);
    params.setAcquireSession("session-123");
    params.setReleaseSession("session-456");

    assertThat(params.getFlags()).isEqualTo(42L);
    assertThat(params.getCas()).isEqualTo(123L);
    assertThat(params.getAcquireSession()).isEqualTo("session-123");
    assertThat(params.getReleaseSession()).isEqualTo("session-456");
  }

  @Test
  void testNewServiceAllOptions() {
    NewService service = new NewService();
    service.setId("service-1");
    service.setName("web-service");
    service.setTags(java.util.List.of("web", "api", "v1"));
    service.setAddress("192.168.1.100");
    service.setPort(8080);
    service.setEnableTagOverride(true);

    assertThat(service.getId()).isEqualTo("service-1");
    assertThat(service.getName()).isEqualTo("web-service");
    assertThat(service.getTags()).containsExactly("web", "api", "v1");
    assertThat(service.getAddress()).isEqualTo("192.168.1.100");
    assertThat(service.getPort()).isEqualTo(8080);
    assertThat(service.getEnableTagOverride()).isTrue();
  }

  @Test
  void testConsulRawClientWithCustomExecutor() {
    var executor = Executors.newFixedThreadPool(2);

    var client =
        new ConsulRawClient.Builder(consul.getHost(), consul.getMappedPort(8500))
            .setExecutor(executor)
            .build();

    var response = client.makeGetRequest("/v1/agent/self");
    assertThat(response.getStatusCode()).isEqualTo(200);

    executor.shutdown();
  }

  @Test
  void testConsulRawClientWithCustomPath() {
    var client =
        new ConsulRawClient.Builder(consul.getHost(), consul.getMappedPort(8500), "/v1").build();

    var response = client.makeGetRequest("/agent/self");
    assertThat(response.getStatusCode()).isEqualTo(200);
  }

  @Test
  void testRequestBuilderFullConfiguration() {
    Request request =
        new Request.Builder("/v1/kv/test-key").setContent("test-value".getBytes()).build();

    assertThat(request.getEndpoint()).isEqualTo("/v1/kv/test-key");
    assertThat(request.getContent()).isEqualTo("test-value".getBytes());
  }

  @Test
  void testVeryLongKeyName() {
    StringBuilder longKey = new StringBuilder("test/");
    for (int i = 0; i < 100; i++) {
      longKey.append("very-long-key-segment-").append(i).append("/");
    }
    longKey.append("final-key");

    String key = longKey.toString();
    String value = "value-for-long-key";

    var putResponse = consulClient.setKVValue(key, value);
    assertThat(putResponse.getValue()).isTrue();

    var getResponse = consulClient.getKVValue(key);
    assertThat(getResponse.getValue().getDecodedValue()).isEqualTo(value);

    consulClient.deleteKVValue(key);
  }

  @Test
  void testUnicodeInValues() {
    String key = "test/unicode";
    String unicodeValue = "Hello 世界! Здравствуй мир! مرحبا بالعالم! 🌍🚀";

    var putResponse = consulClient.setKVValue(key, unicodeValue);
    assertThat(putResponse.getValue()).isTrue();

    var getResponse = consulClient.getKVValue(key);
    assertThat(getResponse.getValue().getDecodedValue()).isEqualTo(unicodeValue);

    consulClient.deleteKVValue(key);
  }

  @Test
  void testConcurrentKVOperations() throws InterruptedException {
    int numThreads = 5;
    Thread[] threads = new Thread[numThreads];
    String[] keys = new String[numThreads];

    for (int i = 0; i < numThreads; i++) {
      final int threadIndex = i;
      keys[i] = "test/concurrent/" + threadIndex;

      threads[i] =
          new Thread(
              () -> {
                String key = keys[threadIndex];
                String value = "value-" + threadIndex;

                consulClient.setKVValue(key, value);
                var response = consulClient.getKVValue(key);
                assertThat(response.getValue().getDecodedValue()).isEqualTo(value);
              });
    }

    for (Thread thread : threads) {
      thread.start();
    }

    for (Thread thread : threads) {
      thread.join();
    }

    for (String key : keys) {
      consulClient.deleteKVValue(key);
    }
  }

  @Test
  void testErrorHandling() {
    assertThatThrownBy(
            () -> {
              consulClient.getKVValue("nonexistent/key/that/definitely/does/not/exist");
            })
        .doesNotThrowAnyException();

    var response = consulClient.getKVValue("nonexistent/key");
    assertThat(response.getValue()).isNull();
  }

  @Test
  void testInvalidServiceRegistration() {
    NewService invalidService = new NewService();

    assertThatThrownBy(
            () -> {
              consulClient.agentServiceRegister(invalidService);
            })
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  void testQueryParamsEdgeCases() {
    QueryParams params = new QueryParams("test-dc");
    assertThat(params.getDatacenter()).isEqualTo("test-dc");

    QueryParams defaultParams = QueryParams.DEFAULT;
    assertThat(defaultParams).isNotNull();
  }

  @Test
  void testSpecialCharactersInServiceName() {
    String serviceName = "service-with-special-chars!@#$%^&*()";

    NewService service = new NewService();
    service.setName(serviceName);
    service.setPort(8080);

    try {
      consulClient.agentServiceRegister(service);

      var servicesResponse = consulClient.getAgentServices();
      boolean found =
          servicesResponse.getValue().values().stream()
              .anyMatch(s -> serviceName.equals(s.getService()));

      if (found) {
        consulClient.agentServiceDeregister(serviceName);
      }
    } catch (Exception e) {
      // Expected for invalid service names
    }
  }

  @Test
  void testLargeNumberOfServices() {
    int serviceCount = 50;
    String[] serviceIds = new String[serviceCount];

    try {
      for (int i = 0; i < serviceCount; i++) {
        serviceIds[i] = "bulk-service-" + i;
        NewService service = new NewService();
        service.setId(serviceIds[i]);
        service.setName("bulk-test");
        service.setPort(8000 + i);

        consulClient.agentServiceRegister(service);
      }

      var servicesResponse = consulClient.getAgentServices();
      long bulkServices =
          servicesResponse.getValue().values().stream()
              .filter(s -> "bulk-test".equals(s.getService()))
              .count();

      assertThat(bulkServices).isEqualTo(serviceCount);

    } finally {
      for (String serviceId : serviceIds) {
        if (serviceId != null) {
          try {
            consulClient.agentServiceDeregister(serviceId);
          } catch (Exception e) {
            // Ignore cleanup errors
          }
        }
      }
    }
  }
}
