package mods.eln.sim2.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Terminals;


public class MultivaluedHashMap<K, V> implements Iterable<V> {
    private final HashMap<K, List<V>> _map;

    public MultivaluedHashMap() {
        _map = new HashMap<K, List<V>>();
    }
    
    public boolean contains(final K key) {
        return _map.containsKey(key);
    }
    
    public boolean put(final K key, final V value) {
        List<V> values = _map.get(key);
        if (values == null) {
            values = new LinkedList();
            _map.put(key, values);
        }
        if (values.contains(value)) {
            return false;
        } 
        else {
            values.add(value);
            return true;
        }
    }
    
    public void clear() {
        _map.clear();
    }
    
    public boolean isEmpty() {
        return _map.isEmpty();
    }
    
    public int size() {
        int size = 0;
        for (List<V> values: _map.values()) {
            size += values.size();
        }
        return size;
    }
    
    public boolean remove(final K key, final V value) {
        List<V> values = _map.get(key);
        if (values == null) {
            return false;
        }
        else {
            return values.remove(value);
        }
    }

    public List<V> get(final K key) {
        return _map.get(key);
    }
    
    class MultivaluedHashMapValuesIterator<V> implements Iterator<V> {
        Iterator<List<V>> _outer_iterator;
        Iterator<V> _inner_iterator;
        
        public MultivaluedHashMapValuesIterator(final MultivaluedHashMap<K, V> multivaluedHashMap) {
            _outer_iterator = multivaluedHashMap._map.values().iterator();
            _inner_iterator = _outer_iterator.next().iterator();
        }

        @Override
        public boolean hasNext() {
            while (!_inner_iterator.hasNext()) {
                if (_outer_iterator.hasNext()) {
                    _inner_iterator = _outer_iterator.next().iterator();
                }
                else {
                    return false;
                }
            }
            return true;
        }

        @Override
        public V next() {
            while (!_inner_iterator.hasNext()) {
                if (_outer_iterator.hasNext()) {
                    _inner_iterator = _outer_iterator.next().iterator();
                }
                else {
                    throw new NoSuchElementException();
                }
            }
            return _inner_iterator.next();
        }

        @Override
        public void remove() {
            _inner_iterator.remove();
        }
        
    }
    
    public Iterator<V> iterator() {
        return new MultivaluedHashMapValuesIterator(this);
    }
}
