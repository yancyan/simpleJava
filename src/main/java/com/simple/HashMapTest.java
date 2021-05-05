package com.simple;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapTest {

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ab", "accc");
        System.out.println(map);

        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("abc", 23);
        System.out.println(concurrentHashMap);
    }
}
