package kata.cache;

import java.util.Objects;

public final class CacheValue {
  private final String value;

  public CacheValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("CacheValue must not be null");
    }
    this.value = value;
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CacheValue other)) return false;
    return value.equals(other.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value;
  }
}
