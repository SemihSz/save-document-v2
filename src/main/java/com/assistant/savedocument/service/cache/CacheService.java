package com.assistant.savedocument.service.cache;

import org.springframework.stereotype.Service;

/**
 * Created by Semih, 25.09.2023
 */
@Service
public interface CacheService<K, V> {

    V get(K key);
    void put(K key, V value);
    void evict(K key);
    void clear();
}
