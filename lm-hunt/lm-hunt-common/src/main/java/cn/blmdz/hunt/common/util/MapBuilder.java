package cn.blmdz.hunt.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * email:dong_peiji@huateng.com
 * Created by 董培基 on 2016/3/3.
 */
public class MapBuilder<K, V> {
    private Map<K, V> map;
    private boolean ignoreNullValue = false;

    private MapBuilder() {
        this.map = new HashMap<K,V>();
    }

    private MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    public static <K, V> MapBuilder<K, V> of() {
        return new MapBuilder<K,V>();
    }

    public static <K, V> MapBuilder<K, V> of(Map<K, V> map) {
        return new MapBuilder<K,V>(map);
    }

    public static <K, V> MapBuilder<K, V> newHashMap() {
        return of(new HashMap<K,V>());
    }

    public static <K, V> MapBuilder<K, V> newTreeMap() {
        return of(new TreeMap<K,V>());
    }

    public MapBuilder<K, V> ignoreNullValue() {
        this.ignoreNullValue = true;
        return this;
    }

    public MapBuilder<K, V> put(K key, V value) {
        if (this.ignoreNullValue && value == null) {
            return this;
        } else {
            this.map.put(key, value);
            return this;
        }
    }

    public MapBuilder<K, V> put(K k1, V v1, K k2, V v2) {
        return this.put(k1, v1).put(k2, v2);
    }

    public MapBuilder<K, V> put(K k1, V v1, K k2, V v2, K k3, V v3) {
        return this.put(k1, v1).put(k2, v2).put(k3, v3);
    }

    public MapBuilder<K, V> put(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return this.put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4);
    }

    public MapBuilder<K, V> put(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return this.put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5);
    }

    public Map<K, V> map() {
        return this.map;
    }
}
