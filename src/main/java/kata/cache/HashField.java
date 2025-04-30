package kata.cache;

import java.util.Objects;

public final class HashField {
    private final String value;

    public HashField(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("HashField must not be null or blank");
        }
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashField other)) return false;
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
