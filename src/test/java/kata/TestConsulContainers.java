package kata;

import org.testcontainers.consul.ConsulContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Factory for creating standardized Consul containers for integration tests. Provides consistent
 * configuration and version across all integration tests.
 */
public final class TestConsulContainers {

  private static final String CONSUL_VERSION = "1.21.2";
  private static final DockerImageName CONSUL_IMAGE =
      DockerImageName.parse("hashicorp/consul:" + CONSUL_VERSION);

  private TestConsulContainers() {
    // Utility class
  }

  /**
   * Creates a new ConsulContainer with standardized configuration.
   *
   * @return configured ConsulContainer ready for use in tests
   */
  public static ConsulContainer newContainer() {
    return new ConsulContainer(CONSUL_IMAGE).withReuse(true);
  }
}
