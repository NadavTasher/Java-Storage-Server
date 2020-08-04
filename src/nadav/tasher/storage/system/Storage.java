package nadav.tasher.storage.system;

import java.io.File;

/**
 * This class defines filesystem properties and houses filesystem related functions.
 */
public abstract class Storage {

    // The storage root directory
    public static final File ROOT = new File("out/test");

    /**
     * Initializes the storage system (Creates directories).
     */
    public static void initialize() throws Exception {
        // Make sure the root directory exists
        if (!ROOT.exists())
            // Create the root directory
            if (!ROOT.mkdir())
                // Throw exception
                throw new Exception("Unable to create root directory. Check your permissions.");
    }

}
