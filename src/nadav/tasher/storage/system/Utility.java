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
    public static String toHexadecimal(String string) {
        return null;
    }

    /**
     * Calculates a hash for a given string.
     *
     * @param string String
     * @return Hexadecimal hash
     */
    public static String toHash(String string) {
        return null;
    }

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

}
