package com.simple.data.struct;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Java LinkedHashMap.
 *
 * @author ZhuYX
 * @date 2021/05/31
 */
public class LRU {

    Map<Integer, Integer> cache;
    int size;

    LRU(int size) {
        this.size = size;
        cache = new LinkedHashMap<>(size, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > size;
            }
        };
    }

    public Integer get(Integer key) {
        return cache.getOrDefault(key, -1);
    }
    public void put(int key, int value) {
        cache.put(key, value);
    }

    public static void main(String[] args) {

    }

}
