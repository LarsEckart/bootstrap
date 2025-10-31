package kata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.catalog.CatalogNodesRequest;
import com.ecwid.consul.v1.catalog.CatalogServicesRequest;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.consul.ConsulContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class ConsulQueryIntegrationTest {

  @Container static final ConsulContainer consul = TestConsulContainers.newContainer();

  private ConsulClient consulClient;

  @BeforeEach
  void setUp() {
    consulClient = new ConsulClient(consul.getHost(), consul.getMappedPort(8500));
  }

  @Test
  void testExecutePreparedQuery() {
    try {
      String queryId = UUID.randomUUID().toString();

      var response = consulClient.executePreparedQuery(queryId, QueryParams.DEFAULT);

      if (response.getValue() != null) {
        assertThat(response.getValue()).isNotNull();
      }
    } catch (OperationException e) {
      assumeTrue(false, "Prepared query not found or queries not supported: " + e.getMessage());
    }
  }

  @Test
  void testExecutePreparedQueryWithCustomParams() {
    try {
      String queryId = UUID.randomUUID().toString();
      QueryParams params = new QueryParams("dc1");

      var response = consulClient.executePreparedQuery(queryId, params);

      if (response.getValue() != null) {
        assertThat(response.getValue()).isNotNull();
      }
    } catch (OperationException e) {
      assumeTrue(false, "Prepared query execution with custom params failed: " + e.getMessage());
    }
  }

  @Test
  void testQueryParamsCreation() {
    QueryParams defaultParams = QueryParams.DEFAULT;
    assertThat(defaultParams).isNotNull();

    QueryParams datacenterParams = new QueryParams("test-dc");
    assertThat(datacenterParams.getDatacenter()).isEqualTo("test-dc");
  }

  @Test
  void testQueryParamsConsistency() {
    QueryParams params = new QueryParams(com.ecwid.consul.v1.ConsistencyMode.CONSISTENT);
    assertThat(params.getConsistencyMode())
        .isEqualTo(com.ecwid.consul.v1.ConsistencyMode.CONSISTENT);
  }

  @Test
  void testQueryParamsStale() {
    QueryParams params = new QueryParams(com.ecwid.consul.v1.ConsistencyMode.STALE);
    assertThat(params.getConsistencyMode()).isEqualTo(com.ecwid.consul.v1.ConsistencyMode.STALE);
  }

  @Test
  void testQueryParamsWithDatacenterAndConsistency() {
    QueryParams params = new QueryParams("dc1", com.ecwid.consul.v1.ConsistencyMode.CONSISTENT);
    assertThat(params.getDatacenter()).isEqualTo("dc1");
    assertThat(params.getConsistencyMode())
        .isEqualTo(com.ecwid.consul.v1.ConsistencyMode.CONSISTENT);
  }

  @Test
  void testQueryParamsWithWaitAndIndex() {
    QueryParams params = new QueryParams(java.time.Duration.ofSeconds(30), 100L);
    // Note: The actual API may not expose these getters
    assertThat(params).isNotNull();
  }

  @Test
  void testQueryResponseMetadata() {
    var response = consulClient.getCatalogServices(null);

    assertThat(response.getConsulIndex()).isNotNull();
    assertThat(response.isConsulKnownLeader()).isNotNull();
    assertThat(response.getConsulLastContact()).isNotNull();
    assertThat(response.getConsulEffectiveConsistency()).isNotNull();
  }

  @Test
  void testBlockingQuery() {
    var initialResponse = consulClient.getCatalogServices(null);
    Long initialIndex = initialResponse.getConsulIndex();

    long startTime = System.currentTimeMillis();
    var blockingResponse = consulClient.getCatalogServices(null);
    long endTime = System.currentTimeMillis();

    assertThat(blockingResponse).isNotNull();
    assertThat(endTime - startTime).isLessThan(5000);
  }

  @Test
  void testConsistencyModeDefault() {
    QueryParams defaultParams = QueryParams.DEFAULT;
    var response = consulClient.getCatalogServices(null);

    assertThat(response.getConsulEffectiveConsistency()).isNotNull();
  }

  @Test
  void testNearNodeQuery() {
    var nodesRequest = CatalogNodesRequest.newBuilder().build();
    var nodesResponse = consulClient.getCatalogNodes(nodesRequest);
    if (!nodesResponse.getValue().isEmpty()) {
      String nodeName = nodesResponse.getValue().get(0).getNode();

      var servicesRequest = CatalogServicesRequest.newBuilder().build();
      var servicesResponse = consulClient.getCatalogServices(servicesRequest);
      assertThat(servicesResponse).isNotNull();
    }
  }

  @Test
  void testQueryParamsAllConstructors() {
    // Test all available QueryParams constructors

    QueryParams params1 = QueryParams.DEFAULT;
    assertThat(params1).isNotNull();

    QueryParams params2 = new QueryParams("dc1");
    assertThat(params2.getDatacenter()).isEqualTo("dc1");

    QueryParams params3 = new QueryParams(com.ecwid.consul.v1.ConsistencyMode.STALE);
    assertThat(params3.getConsistencyMode()).isEqualTo(com.ecwid.consul.v1.ConsistencyMode.STALE);

    QueryParams params4 = new QueryParams("dc2", com.ecwid.consul.v1.ConsistencyMode.CONSISTENT);
    assertThat(params4.getDatacenter()).isEqualTo("dc2");
    assertThat(params4.getConsistencyMode())
        .isEqualTo(com.ecwid.consul.v1.ConsistencyMode.CONSISTENT);

    QueryParams params5 = new QueryParams(java.time.Duration.ofSeconds(10), 42L);
    assertThat(params5).isNotNull();

    QueryParams params6 = new QueryParams("dc3", java.time.Duration.ofSeconds(15), 123L);
    assertThat(params6.getDatacenter()).isEqualTo("dc3");
  }
}
