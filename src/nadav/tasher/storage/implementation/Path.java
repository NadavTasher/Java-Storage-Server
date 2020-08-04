package nadav.tasher.storage.implementation;

import nadav.tasher.storage.area.Application;
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
        this.directory = new File(parent, Utility.encode(this.name));
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

    /**
     * Creates a path from a context string.
     *
     * @param string Context string
     * @return Path
     */
    public static Path fromString(String string) {
        // Create the returned path
        Path path = null;

        // Split the context into parts
        String[] context = string.split(":", 4);

        // Check length of parts
        if (context.length >= 1) {
            // Create new application
            path = new Application(context[0]);
        }

        // Check length of parts
        if (context.length >= 2) {
            // Create new table
            path = ((Application) path).table(context[1]);
        }

        // Check length of parts
        if (context.length >= 3) {
            // Create new row
            path = ((Application.Table) path).row(context[2]);
        }

        // Check length of parts
        if (context.length >= 4) {
            // Create new row
            path = ((Application.Table.Row) path).column(context[3]);
        }

        // Return the created path
        return path;
    }
}
