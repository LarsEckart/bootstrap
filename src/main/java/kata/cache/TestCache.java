package kata.cache;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TestCache implements Cache {
    private final ConcurrentMap<CacheKey, CacheValue> kvStore = new ConcurrentHashMap<>();
    private final ConcurrentMap<HashName, ConcurrentMap<HashField, CacheValue>> hashStore = new ConcurrentHashMap<>();

    @Override
    public Optional<CacheValue> get(CacheKey key) {
        return Optional.ofNullable(kvStore.get(key));
    }

    @Override
    public CacheValue getOrThrow(CacheKey key) {
        var value = kvStore.get(key);
        if (value == null) throw new RuntimeException("Key not found: " + key.value());
        return value;
    }

    @Override
    public void put(CacheKey key, CacheValue value) {
        kvStore.put(key, value);
    }

    @Override
    public void remove(CacheKey key) {
        kvStore.remove(key);
    }

    @Override
    public boolean exists(CacheKey key) {
        return kvStore.containsKey(key);
    }

    @Override
    public Optional<CacheValue> hget(HashName hash, HashField field) {
        var map = hashStore.get(hash);
        if (map == null) return Optional.empty();
        return Optional.ofNullable(map.get(field));
    }

    @Override
    public CacheValue hgetOrThrow(HashName hash, HashField field) {
        var map = hashStore.get(hash);
        if (map == null) throw new RuntimeException("Hash not found: " + hash.value());
        var value = map.get(field);
        if (value == null) throw new RuntimeException("Field not found: " + field.value());
        return value;
    }

    @Override
    public void hset(HashName hash, HashField field, CacheValue value) {
        hashStore.computeIfAbsent(hash, h -> new ConcurrentHashMap<>()).put(field, value);
    }

    @Override
    public void hdel(HashName hash, HashField field) {
        var map = hashStore.get(hash);
        if (map != null) map.remove(field);
    }
}
