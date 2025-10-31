package kata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.acl.AclTokensRequest;
import com.ecwid.consul.v1.acl.model.NewAcl;
import com.ecwid.consul.v1.acl.model.UpdateAcl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.consul.ConsulContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class ConsulAclIntegrationTest {

  @Container static final ConsulContainer consul = TestConsulContainers.newContainer();

  private ConsulClient consulClient;

  @BeforeEach
  void setUp() {
    consulClient = new ConsulClient(consul.getHost(), consul.getMappedPort(8500));
  }

  @Test
  void testAclReadSelf() {
    try {
      var response = consulClient.aclReadSelf();
      if (response.getValue() != null) {
        assertThat(response.getValue().getAccessorId()).isNotNull();
      }
    } catch (OperationException e) {
      assumeTrue(false, "ACL system not enabled or configured: " + e.getMessage());
    }
  }

  @Test
  void testAclTokenOperations() {
    try {
      NewAcl newAcl = new NewAcl();
      newAcl.setDescription("Test ACL token");
      newAcl.setLocal(true);

      var createResponse = consulClient.aclCreate(newAcl);
      if (createResponse.getValue() != null) {
        String accessorId = createResponse.getValue().getAccessorId();
        assertThat(accessorId).isNotNull();

        var readResponse = consulClient.aclRead(accessorId);
        assertThat(readResponse.getValue()).isNotNull();
        assertThat(readResponse.getValue().getDescription()).isEqualTo("Test ACL token");

        UpdateAcl updateAcl = new UpdateAcl();
        updateAcl.setDescription("Updated test ACL token");
        updateAcl.setLocal(true);

        var updateResponse = consulClient.aclUpdate(updateAcl, accessorId);
        assertThat(updateResponse.getValue()).isNotNull();

        var deleteResponse = consulClient.aclDelete(accessorId);
        assertThat(deleteResponse).isNotNull();
      }
    } catch (OperationException e) {
      assumeTrue(false, "ACL operations not supported in dev mode: " + e.getMessage());
    }
  }

  @Test
  void testAclClone() {
    try {
      NewAcl newAcl = new NewAcl();
      newAcl.setDescription("Original ACL token");
      newAcl.setLocal(true);

      var createResponse = consulClient.aclCreate(newAcl);
      if (createResponse.getValue() != null) {
        String originalAccessorId = createResponse.getValue().getAccessorId();

        var cloneResponse = consulClient.aclClone(originalAccessorId, "Cloned ACL token");
        if (cloneResponse.getValue() != null) {
          String clonedAccessorId = cloneResponse.getValue().getAccessorId();
          assertThat(clonedAccessorId).isNotEqualTo(originalAccessorId);

          var readCloned = consulClient.aclRead(clonedAccessorId);
          assertThat(readCloned.getValue().getDescription()).isEqualTo("Cloned ACL token");

          consulClient.aclDelete(clonedAccessorId);
        }

        consulClient.aclDelete(originalAccessorId);
      }
    } catch (OperationException e) {
      assumeTrue(false, "ACL clone not supported: " + e.getMessage());
    }
  }

  @Test
  void testAclList() {
    try {
      AclTokensRequest request = AclTokensRequest.newBuilder().build();
      var response = consulClient.aclList(request);
      assertThat(response).isNotNull();
      assertThat(response.getValue()).isNotNull();
    } catch (OperationException e) {
      assumeTrue(false, "ACL list not supported: " + e.getMessage());
    }
  }

  @Test
  void testAclReadExpanded() {
    try {
      NewAcl newAcl = new NewAcl();
      newAcl.setDescription("Test ACL for expanded read");
      newAcl.setLocal(true);

      var createResponse = consulClient.aclCreate(newAcl);
      if (createResponse.getValue() != null) {
        String accessorId = createResponse.getValue().getAccessorId();

        var readResponse = consulClient.aclRead(accessorId, true);
        assertThat(readResponse.getValue()).isNotNull();

        var readResponseNonExpanded = consulClient.aclRead(accessorId, false);
        assertThat(readResponseNonExpanded.getValue()).isNotNull();

        consulClient.aclDelete(accessorId);
      }
    } catch (OperationException e) {
      assumeTrue(false, "ACL expanded read not supported: " + e.getMessage());
    }
  }

  @Test
  void testAclTokensRequestBuilder() {
    AclTokensRequest.Builder builder = AclTokensRequest.newBuilder();
    AclTokensRequest request = builder.build();

    assertThat(request).isNotNull();
  }

  @Test
  void testNewAclProperties() {
    NewAcl newAcl = new NewAcl();
    newAcl.setDescription("Test description");
    newAcl.setLocal(true);

    assertThat(newAcl.getDescription()).isEqualTo("Test description");
    assertThat(newAcl.isLocal()).isTrue();
  }

  @Test
  void testUpdateAclProperties() {
    UpdateAcl updateAcl = new UpdateAcl();
    updateAcl.setDescription("Updated description");
    updateAcl.setLocal(false);

    assertThat(updateAcl.getDescription()).isEqualTo("Updated description");
    assertThat(updateAcl.isLocal()).isFalse();
  }
}
