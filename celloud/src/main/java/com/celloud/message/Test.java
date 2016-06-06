package com.celloud.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;

public class Test {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Map<String, Object> map = MapUtils.putAll(new HashMap<String, Object>(), new KeyValue[] {
                new DefaultKeyValue("a1", new DefaultMessageHandler()), new DefaultKeyValue("c", "d") });
        for (String key : map.keySet()) {
            System.out.println("key = " + key + " , value = " + map.get(key) + " ");
        }
    }
}
