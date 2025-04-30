package kata.cache;

import java.util.Optional;

public interface Cache {
    Optional<CacheValue> get(CacheKey key) throws CacheException;
    CacheValue getOrThrow(CacheKey key) throws CacheException;
    void put(CacheKey key, CacheValue value) throws CacheException;
    void remove(CacheKey key) throws CacheException;
    boolean exists(CacheKey key) throws CacheException;

    Optional<CacheValue> hget(HashName hash, HashField field) throws CacheException;
    CacheValue hgetOrThrow(HashName hash, HashField field) throws CacheException;
    void hset(HashName hash, HashField field, CacheValue value) throws CacheException;
    void hdel(HashName hash, HashField field) throws CacheException;
}
