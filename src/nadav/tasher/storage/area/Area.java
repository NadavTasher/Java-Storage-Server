package nadav.tasher.storage.area;

import nadav.tasher.storage.system.Storage;
import nadav.tasher.storage.system.Utility;

import java.io.File;
import java.util.ArrayList;

public class Area {

    // The area's name
    private String name;

    // The directory in which files get stored
    private File directory;

    // Keystores
    private Keystore configuration;
    private ArrayList<Keystore> keystores;

    public Area(String name) throws Exception {
        // Initialize name
        this.name = name;

        // Initialize directory
        this.directory = new File(Storage.ROOT, Utility.toHexadecimal(name));

        // Initialize keystores
        this.keystores = new ArrayList<>();

        // Initialize in filesystem
        this.initialize();
    }

    /**
     * Initializes the area in the filesystem (Creates directories).
     */
    private void initialize() throws Exception {
        // Make sure the directory exists
        if (!this.directory.exists())
            // Create the directory
            if (!this.directory.mkdir())
                // Throw exception
                throw new Exception("Unable to create root directory. Check your permissions.");

        // Initialize the configuration keystore
        this.configuration = new Keystore("configuration");
    }

}
