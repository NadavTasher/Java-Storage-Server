package nadav.tasher.storage.area;

import nadav.tasher.storage.implementation.Path;
import nadav.tasher.storage.system.Storage;

import java.io.File;

public class Area extends Path {

    // Keystores
    private Keystore configuration;

    /**
     * Area constructor.
     *
     * @param name Area name
     */
    public Area(String name) {
        // Initialize path
        super(Storage.ROOT, name);

        // Initialize the configuration keystore
        this.configuration = new Keystore(this, "configuration");
    }

    /**
     * Creates a keystore object.
     *
     * @param name Keystore name
     * @return Keystore object
     */
    public Keystore keystore(String name) {
        // Create the keystore object
        return new Keystore(this, name);
    }

    /**
     * Returns the area's directory.
     *
     * @return Area directory
     */
    public File getDirectory() {
        return super.getDirectory();
    }
}
