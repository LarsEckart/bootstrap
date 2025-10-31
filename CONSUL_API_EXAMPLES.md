# Consul API Library Usage Examples

This document provides comprehensive examples of how to use the `io.github.jon5477:consul-api:2.0.0-SNAPSHOT` library correctly.

## Key Points About This Library Version

1. **Request Objects**: This version uses builder pattern request objects instead of direct parameters
2. **Enum Status Values**: Health check statuses are returned as enums, not strings
3. **Base64 Encoding**: Raw API responses return Base64-encoded values in JSON format
4. **Defensive Programming**: Metadata fields may be null and require defensive checks

## Basic Client Setup

```java
import com.ecwid.consul.v1.ConsulClient;

// Basic client with default settings
ConsulClient consulClient = new ConsulClient();

// Client with custom host and port
ConsulClient consulClient = new ConsulClient("localhost", 8500);
```

## Agent Operations

### Service Registration and Management

```java
import com.ecwid.consul.v1.agent.model.NewService;

// Register a service
NewService service = new NewService();
service.setId("my-service-1");
service.setName("my-service");
service.setPort(8080);
service.setAddress("127.0.0.1");
service.setTags(List.of("web", "api"));

var response = consulClient.agentServiceRegister(service);
// response.getValue() will be true if successful

// List all services
var services = consulClient.getAgentServices();
services.getValue().forEach((id, service) -> {
    System.out.printf("Service %s: %s:%d%n", 
        id, service.getAddress(), service.getPort());
});

// Deregister service
consulClient.agentServiceDeregister("my-service-1");
```

### Health Check Management

```java
import com.ecwid.consul.v1.agent.model.NewCheck;

// Register a TTL health check
NewCheck check = new NewCheck();
check.setId("my-check-1");
check.setName("My Service Health");
check.setTtl("30s");
check.setNotes("Checks my service health");

consulClient.agentCheckRegister(check);

// Update check status (IMPORTANT: status is returned as enum, not string)
consulClient.agentCheckPass("my-check-1", "Service is healthy");
var checks = consulClient.getAgentChecks();
String status = checks.getValue().get("my-check-1").getStatus().toString();
// status will be "PASSING", "WARNING", or "CRITICAL"

// Register HTTP health check
NewCheck httpCheck = new NewCheck();
httpCheck.setId("http-check");
httpCheck.setName("HTTP Health Check");
httpCheck.setHttp("http://localhost:8080/health");
httpCheck.setInterval("10s");
httpCheck.setTimeout("3s");

consulClient.agentCheckRegister(httpCheck);
```

## Key-Value Store Operations

### Basic KV Operations

```java
import com.ecwid.consul.v1.kv.model.PutParams;

// Simple put/get
String key = "config/app/database_url";
String value = "postgresql://localhost:5432/mydb";

var putResponse = consulClient.setKVValue(key, value);
if (putResponse.getValue()) {
    System.out.println("Key stored successfully");
}

// Get value
var getResponse = consulClient.getKVValue(key);
if (getResponse.getValue() != null) {
    String retrievedValue = getResponse.getValue().getDecodedValue();
    System.out.println("Retrieved: " + retrievedValue);
}

// Binary data
byte[] binaryData = "binary content".getBytes(StandardCharsets.UTF_8);
consulClient.setKVBinaryValue("config/binary", binaryData);

var binaryResponse = consulClient.getKVBinaryValue("config/binary");
byte[] retrieved = binaryResponse.getValue().getValue();
```

### Advanced KV Operations with Parameters

```java
import com.ecwid.consul.v1.kv.model.PutParams;

// Conditional put (only if key doesn't exist)
PutParams putParams = new PutParams();
putParams.setCas(0L); // Compare-and-swap with 0 means "only if not exists"

var conditionalPut = consulClient.setKVValue(key, value, putParams);
if (!conditionalPut.getValue()) {
    System.out.println("Key already exists");
}

// Prefix operations
String prefix = "config/app/";
var prefixValues = consulClient.getKVValues(prefix);
prefixValues.getValue().forEach(kv -> {
    System.out.printf("%s = %s%n", kv.getKey(), kv.getDecodedValue());
});

// Delete with prefix
consulClient.deleteKVValues(prefix);
```

## Catalog Operations

### Node and Service Discovery

```java
import com.ecwid.consul.v1.catalog.CatalogNodesRequest;
import com.ecwid.consul.v1.catalog.CatalogServicesRequest;
import com.ecwid.consul.v1.QueryParams;

// List all nodes (IMPORTANT: Use request objects, not QueryParams directly)
var nodesRequest = CatalogNodesRequest.newBuilder()
    .setQueryParams(QueryParams.DEFAULT)
    .build();
var nodes = consulClient.getCatalogNodes(nodesRequest);

nodes.getValue().forEach(node -> {
    System.out.printf("Node: %s at %s%n", node.getNode(), node.getAddress());
});

// List all services
var servicesRequest = CatalogServicesRequest.newBuilder().build();
var services = consulClient.getCatalogServices(servicesRequest);

services.getValue().forEach((serviceName, tags) -> {
    System.out.printf("Service %s with tags: %s%n", serviceName, tags);
});

// Get specific service instances
var serviceInstances = consulClient.getCatalogService("my-service", QueryParams.DEFAULT);
serviceInstances.getValue().forEach(instance -> {
    System.out.printf("Instance: %s:%d%n", 
        instance.getServiceAddress(), instance.getServicePort());
});
```

### Manual Service Registration

```java
import com.ecwid.consul.v1.catalog.model.CatalogRegistration;
import com.ecwid.consul.v1.catalog.model.CatalogDeregistration;

// Register a node in the catalog
CatalogRegistration registration = new CatalogRegistration();
registration.setNode("external-node");
registration.setAddress("192.168.1.100");

var registerResponse = consulClient.catalogRegister(registration);

// Deregister
CatalogDeregistration deregistration = new CatalogDeregistration();
deregistration.setNode("external-node");
consulClient.catalogDeregister(deregistration);
```

## Health Checks

### Service Health Monitoring

```java
import com.ecwid.consul.v1.health.model.HealthService;

// Get healthy instances of a service
var healthyServices = consulClient.getHealthServices("my-service", true, QueryParams.DEFAULT);
healthyServices.getValue().forEach(healthService -> {
    HealthService.Service service = healthService.getService();
    System.out.printf("Healthy instance: %s:%d%n", 
        service.getAddress(), service.getPort());
    
    // Check health status
    healthService.getChecks().forEach(check -> {
        System.out.printf("  Check %s: %s%n", 
            check.getName(), check.getStatus().toString());
    });
});

// Get health checks for a specific node
var nodeChecks = consulClient.getHealthChecksForNode("consul-node", QueryParams.DEFAULT);
nodeChecks.getValue().forEach(check -> {
    System.out.printf("Check %s: %s - %s%n", 
        check.getName(), check.getStatus().toString(), check.getOutput());
});
```

## Sessions

### Distributed Locking

```java
import com.ecwid.consul.v1.session.model.NewSession;

// Create a session for locking
NewSession newSession = new NewSession();
newSession.setName("my-lock-session");
newSession.setTtl(60); // 60 seconds TTL

var sessionResponse = consulClient.sessionCreate(newSession, QueryParams.DEFAULT);
String sessionId = sessionResponse.getValue();

// Try to acquire a lock
String lockKey = "locks/my-resource";
boolean acquired = consulClient.setKVValue(lockKey, sessionId, 
    new PutParams().setAcquireSession(sessionId)).getValue();

if (acquired) {
    try {
        // Do work with lock held
        System.out.println("Lock acquired, doing work...");
        Thread.sleep(1000);
    } finally {
        // Release lock
        consulClient.setKVValue(lockKey, "", 
            new PutParams().setReleaseSession(sessionId));
        consulClient.sessionDestroy(sessionId, QueryParams.DEFAULT);
    }
}
```

## Raw Client Usage (Advanced)

### Direct HTTP API Access

```java
import com.ecwid.consul.transport.RawRequest;
import com.ecwid.consul.transport.RawResponse;

// For advanced users who need direct HTTP access
// Note: Consul returns JSON with Base64-encoded values
var rawClient = consulClient.getRawClient();

// PUT to KV store
byte[] content = "raw value".getBytes(StandardCharsets.UTF_8);
var putResponse = rawClient.makePutRequest("/v1/kv/raw-key", content);
// putResponse.getContent().toString() will be "true"

// GET from KV store (returns JSON with Base64 value)
var getResponse = rawClient.makeGetRequest("/v1/kv/raw-key");
String jsonResponse = getResponse.getContent().toString();
// jsonResponse contains: [{"Key":"raw-key","Value":"base64-encoded-value",...}]

// Parse JSON to extract and decode the value if needed
```

## Query Parameters and Consistency

### Consistency Modes and Blocking Queries

```java
import com.ecwid.consul.v1.ConsistencyMode;

// Strong consistency
QueryParams strongParams = new QueryParams(ConsistencyMode.CONSISTENT);
var strongResponse = consulClient.getKVValue("important-key", strongParams);

// Stale reads (faster, eventual consistency)
QueryParams staleParams = new QueryParams(ConsistencyMode.STALE);
var staleResponse = consulClient.getKVValue("cache-key", staleParams);

// Blocking query (wait for changes)
Long currentIndex = strongResponse.getConsulIndex();
QueryParams blockingParams = new QueryParams()
    .setWait("30s")
    .setIndex(currentIndex);
    
// This will block until the value changes or timeout
var changedResponse = consulClient.getKVValue("watch-key", blockingParams);
```

## Error Handling Best Practices

### Defensive Programming

```java
// Always check for null values in metadata
var response = consulClient.getAgentSelf();
assertThat(response.getValue()).isNotNull();

if (response.getConsulIndex() != null) {
    Long index = response.getConsulIndex();
    // Use index for blocking queries
}

// Handle service discovery gracefully
var serviceResponse = consulClient.getCatalogService("my-service", QueryParams.DEFAULT);
if (serviceResponse.getValue().isEmpty()) {
    System.out.println("Service not found");
} else {
    // Process service instances
}

// Check health status properly (it's an enum!)
var checks = consulClient.getAgentChecks();
checks.getValue().forEach((id, check) -> {
    switch (check.getStatus()) {
        case PASSING -> System.out.println("Healthy: " + id);
        case WARNING -> System.out.println("Warning: " + id);
        case CRITICAL -> System.out.println("Critical: " + id);
        default -> System.out.println("Unknown status: " + check.getStatus());
    }
});
```

## Common Patterns

### Service Registration with Health Check

```java
// Complete service setup with health monitoring
public void registerServiceWithHealth(String serviceId, String serviceName, 
                                    String address, int port) {
    // Register service
    NewService service = new NewService();
    service.setId(serviceId);
    service.setName(serviceName);
    service.setAddress(address);
    service.setPort(port);
    service.setTags(List.of("api", "v1"));
    
    consulClient.agentServiceRegister(service);
    
    // Add HTTP health check
    NewCheck healthCheck = new NewCheck();
    healthCheck.setId(serviceId + "-health");
    healthCheck.setName(serviceName + " Health Check");
    healthCheck.setServiceId(serviceId);
    healthCheck.setHttp(String.format("http://%s:%d/health", address, port));
    healthCheck.setInterval("10s");
    healthCheck.setTimeout("3s");
    healthCheck.setDeregisterCriticalServiceAfter("1m");
    
    consulClient.agentCheckRegister(healthCheck);
}
```

### Configuration Management

```java
// Hierarchical configuration pattern
public void setupConfiguration() {
    Map<String, String> config = Map.of(
        "app/database/host", "localhost",
        "app/database/port", "5432",
        "app/database/name", "myapp",
        "app/cache/ttl", "3600",
        "app/feature/new_ui", "true"
    );
    
    config.forEach((key, value) -> {
        consulClient.setKVValue(key, value);
    });
}

// Read configuration
public Map<String, String> getConfiguration(String prefix) {
    var response = consulClient.getKVValues(prefix);
    return response.getValue().stream()
        .collect(Collectors.toMap(
            kv -> kv.getKey(),
            kv -> kv.getDecodedValue()
        ));
}
```

This document covers the most common usage patterns and important considerations when working with the consul-api library. Remember to always handle null values defensively and use the correct request objects instead of passing raw parameters.