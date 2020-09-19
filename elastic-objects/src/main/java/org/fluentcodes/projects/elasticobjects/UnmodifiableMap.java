package org.fluentcodes.projects.elasticobjects;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class UnmodifiableMap<K,V> implements Map<K,V>, Serializable {
    private static final long serialVersionUID = -1034234728574286014L;

    private final Map<? extends K, ? extends V> m;

    public UnmodifiableMap(Map<? extends K, ? extends V> m) {
        if (m == null)
            throw new NullPointerException();
        this.m = m;
    }

    public int size() {
        return m.size();
    }

    public boolean isEmpty() {
        return m.isEmpty();
    }

    public boolean containsKey(Object key) {
        return m.containsKey(key);
    }

    public boolean containsValue(Object val) {
        return m.containsValue(val);
    }

    public V get(Object key) {
        return m.get(key);
    }

    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    private transient Set<K> keySet;
    private transient Set<Map.Entry<K, V>> entrySet;
    private transient Collection<V> values;

    public Set<K> keySet() {
        if (keySet == null)
            keySet = (Set<K>) new UnmodifiableSet<>(m.keySet());
        return keySet;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        if (entrySet == null)
            entrySet = new UnmodifiableSet<>(new HashSet<>());
        return entrySet;
    }

    public Collection<V> values() {
        if (values == null)
            values = new UnmodifiableCollection<>(m.values());
        return values;
    }

    public boolean equals(Object o) {
        return o == this || m.equals(o);
    }

    public int hashCode() {
        return m.hashCode();
    }

    public String toString() {
        return m.toString();
    }

    // Override default methods in Map
    @Override
    @SuppressWarnings("unchecked")
    public V getOrDefault(Object k, V defaultValue) {
        // Safe cast as we don't change the value
        return ((Map<K, V>) m).getOrDefault(k, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        m.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V replace(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V computeIfPresent(K key,
                              BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V compute(K key,
                     BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V merge(K key, V value,
                   BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }
}