package com.assistant.savedocument.service.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Created by Semih, 25.09.2023
 */
@Service
public class EhCacheService<K, V> implements CacheService<K, V> {
    private final CacheManager cacheManager;

    public EhCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public V get(K key) {
        Cache cache = cacheManager.getCache("myCache");
        Cache.ValueWrapper wrapper = cache.get(key);
        return wrapper != null ? (V) wrapper.get() : null;
    }

    @Override
    public void put(K key, V value) {
        Cache cache = cacheManager.getCache("myCache");
        cache.put(key, value);
    }

    @Override
    public void evict(K key) {
        Cache cache = cacheManager.getCache("myCache");
        cache.evict(key);
    }

    @Override
    public void clear() {
        Cache cache = cacheManager.getCache("myCache");
        cache.clear();
    }
}
