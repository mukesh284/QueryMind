package com.querymind.service.cache;

import com.querymind.entity.QueryHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory cache service for query results
 */
@Service
@Slf4j
public class CacheService {
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private static final long CACHE_TTL_MINUTES = 60;

    /**
     * Get cached query result
     */
    public Optional<String> get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null) {
            return Optional.empty();
        }

        if (isExpired(entry)) {
            cache.remove(key);
            return Optional.empty();
        }

        log.debug("Cache hit for key: {}", key);
        return Optional.of(entry.getValue());
    }

    /**
     * Put value in cache
     */
    public void put(String key, String value) {
        cache.put(key, new CacheEntry(value, System.currentTimeMillis()));
        log.debug("Cached value for key: {}", key);
    }

    /**
     * Clear cache
     */
    public void clear() {
        cache.clear();
        log.info("Cache cleared");
    }

    /**
     * Check if entry is expired
     */
    private boolean isExpired(CacheEntry entry) {
        long ageMinutes = (System.currentTimeMillis() - entry.getTimestamp()) / (1000 * 60);
        return ageMinutes > CACHE_TTL_MINUTES;
    }

    /**
     * Get cache size
     */
    public int size() {
        return cache.size();
    }

    /**
     * Inner class for cache entries
     */
    private static class CacheEntry {
        private final String value;
        private final long timestamp;

        public CacheEntry(String value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }

        public String getValue() {
            return value;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}

