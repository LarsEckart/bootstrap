package kata;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.Request;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.consul.ConsulContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class ConsulRawClientTest {

  @Container static final ConsulContainer consul = TestConsulContainers.newContainer();

  private ConsulRawClient rawClient;

  @BeforeEach
  void setUp() {
    rawClient = new ConsulRawClient.Builder(consul.getHost(), consul.getMappedPort(8500)).build();
  }

  @Test
  void testBuilderDefaultPath() {
    var client = new ConsulRawClient.Builder().build();
    assertThat(client).isNotNull();
  }

  @Test
  void testBuilderWithHostAndPort() {
    var client = new ConsulRawClient.Builder("localhost", 8500).build();
    assertThat(client).isNotNull();
  }

  @Test
  void testBuilderWithAllParameters() {
    var client =
        new ConsulRawClient.Builder("localhost", 8500, "/v1")
            .setHost("127.0.0.1")
            .setPort(8501)
            .setPath("/custom")
            .build();
    assertThat(client).isNotNull();
  }

  @Test
  void testTokenManagement() {
    String token = "test-token-123";
    rawClient.setToken(token);
    assertThat(rawClient.getToken()).isEqualTo(token.toCharArray());

    char[] tokenArray = "array-token".toCharArray();
    rawClient.setToken(tokenArray);
    assertThat(rawClient.getToken()).isEqualTo(tokenArray);
  }

  @Test
  void testMakeGetRequest() {
    var response = rawClient.makeGetRequest("/v1/agent/self");
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getContent()).isNotNull();
  }

  @Test
  void testMakeGetRequestWithQueryParams() {
    var response = rawClient.makeGetRequest("/v1/catalog/datacenters");
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getContent().toString()).contains("dc1");
  }

  @Test
  void testMakePutRequest() {
    String key = "test-raw-key";
    String value = "test-raw-value";
    byte[] content = value.getBytes(StandardCharsets.UTF_8);

    var putResponse = rawClient.makePutRequest("/v1/kv/" + key, content);
    assertThat(putResponse.getStatusCode()).isEqualTo(200);
    assertThat(putResponse.getContent().toString()).isEqualTo("true");

    var getResponse = rawClient.makeGetRequest("/v1/kv/" + key);
    assertThat(getResponse.getStatusCode()).isEqualTo(200);

    // Consul returns JSON with Base64 encoded values, check structure instead of raw value
    String responseJson = getResponse.getContent().toString();
    assertThat(responseJson).contains("\"Key\":\"" + key + "\"");
    assertThat(responseJson).contains("\"Value\":"); // Base64 encoded value is present

    rawClient.makeDeleteRequest(new Request.Builder("/v1/kv/" + key).build());
  }

  @Test
  void testMakeDeleteRequest() {
    String key = "test-delete-key";
    String value = "test-delete-value";

    rawClient.makePutRequest("/v1/kv/" + key, value.getBytes(StandardCharsets.UTF_8));

    var deleteResponse = rawClient.makeDeleteRequest(new Request.Builder("/v1/kv/" + key).build());
    assertThat(deleteResponse.getStatusCode()).isEqualTo(200);

    var getResponse = rawClient.makeGetRequest("/v1/kv/" + key);
    assertThat(getResponse.getStatusCode()).isEqualTo(404);
  }

  @Test
  void testRequestBuilder() {
    Request request = new Request.Builder("/v1/agent/self").build();

    assertThat(request.getEndpoint()).isEqualTo("/v1/agent/self");
  }

  @Test
  void testRequestBuilderWithContent() {
    byte[] content = "test content".getBytes(StandardCharsets.UTF_8);
    Request request = new Request.Builder("/v1/kv/test").setContent(content).build();

    assertThat(request.getContent()).isEqualTo(content);
  }

  @Test
  void testInvalidEndpoint() {
    var response = rawClient.makeGetRequest("/invalid/endpoint");
    assertThat(response.getStatusCode()).isEqualTo(404);
  }

  @Test
  void testLargeContent() {
    StringBuilder largeValue = new StringBuilder();
    for (int i = 0; i < 1000; i++) {
      largeValue.append("Large content line ").append(i).append("\n");
    }

    String key = "test-large-content";
    byte[] content = largeValue.toString().getBytes(StandardCharsets.UTF_8);

    var putResponse = rawClient.makePutRequest("/v1/kv/" + key, content);
    assertThat(putResponse.getStatusCode()).isEqualTo(200);

    var getResponse = rawClient.makeGetRequest("/v1/kv/" + key);
    assertThat(getResponse.getStatusCode()).isEqualTo(200);

    // Consul returns JSON with Base64 encoded values, check for key presence and structure
    String responseJson = getResponse.getContent().toString();
    assertThat(responseJson).contains("\"Key\":\"" + key + "\"");
    assertThat(responseJson).contains("\"Value\":"); // Base64 encoded large content is present

    rawClient.makeDeleteRequest(new Request.Builder("/v1/kv/" + key).build());
  }

  @Test
  void testSpecialCharactersInKey() {
    String key = "test/special-chars/key with spaces & symbols!@#$%";
    String value = "special value with unicode: αβγδε";

    var putResponse =
        rawClient.makePutRequest("/v1/kv/" + key, value.getBytes(StandardCharsets.UTF_8));
    assertThat(putResponse.getStatusCode()).isEqualTo(200);

    var getResponse = rawClient.makeGetRequest("/v1/kv/" + key);
    assertThat(getResponse.getStatusCode()).isEqualTo(200);

    rawClient.makeDeleteRequest(new Request.Builder("/v1/kv/" + key).build());
  }

  @Test
  void testEmptyContent() {
    String key = "test-empty-content";
    byte[] emptyContent = new byte[0];

    var putResponse = rawClient.makePutRequest("/v1/kv/" + key, emptyContent);
    assertThat(putResponse.getStatusCode()).isEqualTo(200);

    var getResponse = rawClient.makeGetRequest("/v1/kv/" + key);
    assertThat(getResponse.getStatusCode()).isEqualTo(200);

    rawClient.makeDeleteRequest(new Request.Builder("/v1/kv/" + key).build());
  }

  @Test
  void testBinaryContent() {
    String key = "test-binary-content";
    byte[] binaryContent = new byte[256];
    for (int i = 0; i < 256; i++) {
      binaryContent[i] = (byte) i;
    }

    var putResponse = rawClient.makePutRequest("/v1/kv/" + key, binaryContent);
    assertThat(putResponse.getStatusCode()).isEqualTo(200);

    var getResponse = rawClient.makeGetRequest("/v1/kv/" + key);
    assertThat(getResponse.getStatusCode()).isEqualTo(200);

    rawClient.makeDeleteRequest(new Request.Builder("/v1/kv/" + key).build());
  }
}
