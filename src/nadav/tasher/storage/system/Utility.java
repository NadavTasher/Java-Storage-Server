package nadav.tasher.storage.system;

import java.io.File;

/**
 * This class houses utility functions.
 */
public abstract class Utility {

    /**
     * Encodes a string as a hexadecimal string.
     *
     * @param string String
     * @return Hexadecimal encoded string
     */
    public static String hexadecimal(String string) {
        // Create result buffer
        StringBuilder result = new StringBuilder();
        // Convert string to bytes
        byte[] bytes = string.getBytes();
        // Loop over bytes in string
        for (int index = 0; index < bytes.length; index++) {
            result.append(String.format("%02x", bytes[index]));
        }
        // Return result buffer
        return result.toString();
    }

    /**
     * Removes a path recursively.
     *
     * @param path Path
     * @throws Exception Exception
     */
    public static void remove(File path) throws Exception {
        // Check whether the path is a file or a directory
        if (path.isDirectory()) {
            // Recursively delete the directory
            for (File child : path.listFiles()) {
                remove(child);
            }
        }
        // Delete the path
        if (!path.delete())
            // Throw exception
            throw new Exception("Unable to remove file. Check your permissions.");
    }

    public static class Tuple<K, V> {

        // Tuple values
        private final K key;
        private final V value;

        /**
         * Tuple constructor.
         * @param key Key
         * @param value Column
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
         * @return Column
         */
        public V getValue() {
            return value;
        }
    }
}
