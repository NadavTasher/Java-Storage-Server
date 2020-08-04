package nadav.tasher.storage.implementation;

public class Tuple<K, V> {

    // Tuple values
    private final K key;
    private final V value;

    /**
     * Tuple constructor.
     * @param key Key
     * @param value Value
     */
    public Tuple(K key, V value) {
        // Initialize variables
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key.
     * @return Key
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns the value.
     * @return Value
     */
    public V getValue() {
        return value;
    }
}
