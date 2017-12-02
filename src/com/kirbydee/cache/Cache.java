package com.kirbydee.cache;

import com.kirbydee.main.Creatable;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Cache<T> implements Creatable {

    protected Map<String, T> cacheMap;

    public T request(String key) {
        if (contains(key))
            return get(key);
        return null;
    }

    public T get(String key) {
        return this.cacheMap.get(key);
    }

    public boolean contains(String key) {
        return this.cacheMap.containsKey(key);
    }

    public void put(String key, T t) {
        this.cacheMap.put(key, t);
    }

    public T remove(String key) {
        return this.cacheMap.remove(key);
    }

    @Override
    public void create() {
        this.cacheMap = new ConcurrentHashMap<>();
    }

    @Override
    public void destroy() {
        this.cacheMap.keySet().forEach(this::remove);
        this.cacheMap.clear();
        this.cacheMap = null;
    }
}
