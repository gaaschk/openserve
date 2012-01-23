package org.gsoft.phoenix.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An object that maps keys to multiple values and allows lookup in the opposite direction. There are two
 * primary differences between this and the {@link java.util.Map}. They are:
 * <ol>
 *   <li>The ability to lookup keys that map to a given value. This is the "reversible" feature.
 *   <li>The ability for a key to map to multiple values. This is the "multi" feature.
 * <ol>
 * 
 * Note that similar caveats apply to using mutable objects in this map as in the {@link java.util.Map}.
 * In fact, this class is more stringent in that mutable objects used as either keys or values will cause
 * undefined behavior if they are changed while in the map.
 * 
 * The specific behavior of this map is similar to that of HashMap and HashSet. That is, no guarantee is
 * made as to the order of iteration over the keys or values or the order of elements in the collection
 * returned by {@link ReversibleMultiMap#get(Object)} or any other methods that return a {@link java.util.Collection}.
 * 
 * As with most Java Collections, the behavior of this object depends on the proper implementation of the
 * standard contract for {@link java.lang.Object#equals(Object)} and {@link java.lang.Object#hashCode()}.
 *
 * @param <K> The type of the keys in the map.
 * @param <V> The type of the values in the map.
 */
public class ReversibleMultiMap<K,V> {
	private final HashMap<K,Set<V>> map1 = new HashMap<K, Set<V>>();
	private final HashMap<V,Set<K>> map2 = new HashMap<V, Set<K>>();
	

	/**
	 * Removes all key-value mappings from this map.
	 */
	public void clear() {
		map1.clear();
		map2.clear();
	}

	/**
	 * Returns <code>true</code> if this map provides a mapping for the specified key.
	 * More formally, returns <code>true</code> if and only if this map contains a
	 * mapping such that <code>key == null ? keyInMap == null : key.equals(keyInMap)</code>.
	 * 
	 * @param key The key whose presence is to be tested.
	 * @return <code>true</code> if the key is present; <code>false</code> otherwise.
	 */
	public boolean containsKey(K key) {
		return map1.containsKey(key);
	}

    /**
     * Returns <code>true</code> if this map provides any mapping to the specified value.
     * More formally, returns <code>true</code> if and only if this map contains a
     * mapping such that <code>value == null ? valueInMap == null : value.equals(valueInMap)</code>.
     * 
     * @param value The value whose presence is to be tested.
     * @return <code>true</code> if the value is present; <code>false</code> otherwise.
     */
	public boolean containsValue(V value) {
		return map2.containsKey(value);
	}

	/**
	 * Returns the collection of values to which this map maps the specified key. Returns
	 * <code>null</code> if the map contains no such mapping. Note that for this map,
	 * a <code>null</code> return value does indicate no mapping exists. If the key were
	 * mapped to <code>null</code>, the return value would be a Collection containing a
	 * <code>null</code> element. This is different from the contract for the corresponding
	 * method: {@link java.util.Map#get(Object)}.
	 * 
	 * The returned collection is not backed by this map. That is, a shallow copy is returned
	 * and therefore changes in the map will not be reflected in the returned collection.
	 * Conversely, changes to the returned collection will not be reflected in the map.
	 * 
	 * @param key The key whose values are to be returned.
	 * @return A copy of the collection of values to which the key is mapped or <code>null</code> if
	 *         there is no mapping for the key.
	 */
	public Collection<V> get(K key) {
		Set<V> values = map1.get(key);
		// keep our internal representation encapsulated
		return values == null? null : new HashSet<V>(values);	
	}
	
    /**
     * Returns the collection of keys for which this map has a mapping to the specified value. Returns
     * <code>null</code> if the map contains no such mappings. If the value were
     * mapped from <code>null</code>, the returned value would be a Collection containing a
     * <code>null</code> element. This is part of the "reversible" functionality of this map.
     * 
     * The returned collection is not backed by this map. That is, a shallow copy is returned
     * and therefore changes in the map will not be reflected in the returned collection.
     * Conversely, changes to the returned collection will not be reflected in the map.
     * 
     * @param value The value whose keys are to be returned.
     * @return A copy of the collection of keys from which the value is mapped or <code>null</code> if
     *         the value does not exist in the map.
     */
	public Collection<K> getByValue(V value) {
		Set<K> keys = map2.get(value);
		// keep our internal representation encapsulated
		return keys == null? null : new HashSet<K>(keys);	
	}
	
	/**
	 * Returns <code>true</code> if this map contains no key-value mappings.
	 * 
	 * @return <code>true</code> if this map contains no key-value mappings; <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		// both maps will always be empty in synch
		return map1.isEmpty();
	}

	/**
	 * Returns a view of the keys in this map as a set.
     * The returned set is not backed by this map. That is, a shallow copy is returned
     * and therefore changes in the map will not be reflected in the returned set.
     * Conversely, changes to the returned set will not be reflected in the map.
	 * This is different from the contract of the corresponding {@link java.util.Map#keySet()}.
	 * 
	 * @return A copy of the set of keys for which this map has mappings.
	 */
	public Set<K> keySet() {
		return new HashSet<K>(map1.keySet());
	}
	
    /**
     * Returns a view of the values in this map as a set.
     * The returned set is not backed by this map. That is, a shallow copy is returned
     * and therefore changes in the map will not be reflected in the returned set.
     * Conversely, changes to the returned set will not be reflected in the map.
     * This is different from the contract of the corresponding {@link java.util.Map#keySet()}.
     * 
     * @return A copy of the set of values for which this map has mappings.
     */
	public Set<V> valueSet() {
	    return new HashSet<V>(map2.keySet());
	}

	/**
	 * Associates the specified key with the specified value in this map. If there was
	 * already such an association (mapping), this method effectively does nothing.
	 * 
	 * @param key The key with which the value is to be associated.
	 * @param value The value to be associated with the specified key.
	 * @return The collection of values previously associated with the key or <code>null</code>
	 *         if this map had no mappings for the key previously.
	 */
	public Collection<V> put(K key, V value) {
	    Collection<V> returnCollection = null;
		if( map1.containsKey(key) ){
			Set<V> list = map1.get(key);
			returnCollection = new HashSet<V>(list);
			list.add(value);
		} else {
			Set<V> newList = new HashSet<V>();
			newList.add(value);
			map1.put(key, newList);
		}
		if( map2.containsKey(value) ){
			Set<K> list = map2.get(value);
			list.add(key);
		} else {
			Set<K> newList = new HashSet<K>();
			newList.add(key);
			map2.put(value, newList);
		}
		return returnCollection;
	}

	/**
	 * Copies the mappings in the provided map to this map. This is equivalent to
	 * calling {@link #put(Object, Object)} on this map for every key-value mapping
	 * in the provided map. Based upon the contract for that method, if this map
	 * already contains mappings identical to those provided, those mappings are
	 * effectively ignored.
	 * 
	 * @param addMap The mappings to be stored in this map.
	 */
	public void putAll(Map<K, V> addMap) {
		Set<Map.Entry<K, V>> entrySet = addMap.entrySet();
		for (Map.Entry<K, V> entry : entrySet) {
			put(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Removes all the mappings for this key from this map. If any of the values
	 * mapped to the key no longer have mappings, they are completely removed as well.
	 * 
	 * Returns the collection of values to which the provided key was mapped.
	 * 
	 * @param key The key to be removed from the map.
	 * @return A collection of the values that were mapped to the provided key.
	 */
	public Collection<V> removeKey(K key) {
		Set<V> values = map1.remove(key);
		if( values == null ) return null;
		for (V value : values) {
			this.removeMultiMapping(map2, value, key);
		}
		return new HashSet<V>(values);
	}
	
	/**
	 * Removes the mapping of the provided key to the provided value. If
	 * the key only maps to this value, the key will be removed completely.
	 * That is, after this call, a call to {@link #get(Object)} will return <code>null</code>.
	 * If the value is only mapped to this key, the value will be removed completely
	 * such that a subsequent call to {@link #getByValue(Object)} will return <code>null</code>.
	 * 
	 * Returns the mappings for the provided key as they were before the method was called.
	 * 
	 * @param key The key for which the mapping is to be removed.
	 * @param value The value to remove.
	 * @return The previous mappings for the key.
	 */
	public Collection<V> removeValueForKey(K key, V value) {
	    Set<V> values = map1.get(key);
	    Set<V> oldValues = new HashSet<V>(values);
	    
	    this.removeMultiMapping(map1, key, value);
        this.removeMultiMapping(map2, value, key);
	    
	    return oldValues;
	}

	// Note that S and T are used instead of K and V here because K and V are already "defined" by the class.
    private <S, T> void removeMultiMapping(HashMap<S, Set<T>> map, S key, T value) {
        Set<T> values = map.get(key);
        // remove the value from the reverse map if this was the only mapping
	    if (values.size() == 1) {
	        map.remove(key);
	    }
	    // remove the key from the list
	    else {
	        values.remove(value);
	    }
    }
	
    /**
     * Removes all the mappings for this value from this map. If any of the keys
     * mapped to the value no longer have mappings, they are completely removed as well.
     * 
     * Returns the collection of keys which mapped to this value.
     * 
     * @param value The value to be removed from the map.
     * @return A collection of the keys that mapped to the provided value.
     */
    public Collection<K> removeValue(V value) {
        Set<K> keys = map2.remove(value);
        if (keys == null) return null;
        for (K key : keys) {
            this.removeMultiMapping(map1, key, value);
        }
        return new HashSet<K>(keys);
    }
    
    /**
     * Returns the total number of mappings (from key to value) in this map.
     * That is, sums the number of values for each key.
     * 
     * @return The total number of mappings in this map.
     */
    public int size() {
        int size = 0;
        for (K key : this.keySet()) {
            size += this.get(key).size();
        }
        return size;
    }

    /**
     * Returns the number of unique (per {@link java.lang.Object#equals(Object)}) keys
     * in this map.
     * 
     * @return The number of keys.
     */
    public int sizeOfKeys() {
	    return map1.size();
	}
	
    /**
     * Returns the number of unique (per {@link java.lang.Object#equals(Object)}) values
     * in this map.
     * 
     * @return The number of values.
     */
	public int sizeOfValues() {
		return map2.size();
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (map1.hashCode());
        result = prime * result + (map2.hashCode());
        return result;
    }

	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        // Thanks to type erasure, can't use <K, V> here. Hence SuppressWarnings above. Thanks Java Generics!
        @SuppressWarnings("rawtypes")
		ReversibleMultiMap other = (ReversibleMultiMap) obj;
        if (!map1.equals(other.map1)) {
            return false;
        }
        if (!map2.equals(other.map2)) {
            return false;
        }
        return true;
    }
	
	
}
