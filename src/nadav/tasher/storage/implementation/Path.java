package nadav.tasher.storage.implementation;

import nadav.tasher.storage.system.Utility;

import java.io.File;

public class Path {

    // The area's name
    private String name;

    // The directory in which files get stored
    private File directory;

    /**
     * Path constructor.
     *
     * @param parent Parent directory
     * @param name   Path name
     */
    protected Path(File parent, String name) {
        // Initialize name
        this.name = name;

        // Initialize directory
        this.directory = new File(parent, Utility.toHexadecimal(this.name));
    }

    /**
     * Returns the path's name.
     *
     * @return Path name
     */
    protected String getName() {
        return this.name;
    }

    /**
     * Returns the path's directory.
     *
     * @return Path directory
     */
    protected File getDirectory() {
        return this.directory;
    }

    /**
     * Returns whether the path exists.
     *
     * @return Path existence
     */
    public boolean exists() {
        return this.directory.exists() && this.directory.isDirectory();
    }

    /**
     * Creates the path.
     *
     * @throws Exception Exception
     */
    public void insert() throws Exception {
        // Make sure the directory does not exist
        if (this.exists())
            return;

        // Create the directory
        if (!this.directory.mkdir())
            // Throw exception
            throw new Exception("Unable to create directory. Check your permissions.");
    }

    /**
     * Deletes the path.
     *
     * @throws Exception Exception
     */
    public void remove() throws Exception {
        // Make sure the directory exists
        if (!this.exists())
            return;

        // Remove the path
        Utility.remove(this.directory);
    }
}
