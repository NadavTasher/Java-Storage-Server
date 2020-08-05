package nadav.tasher.storage.server.operations.path;

import nadav.tasher.storage.implementation.Operation;
import nadav.tasher.storage.implementation.Path;

public class Insert extends Operation {

    /**
     * Insert operation constructor.
     *
     * @param path     Path to insert
     * @param argument Argument to work with
     */
    public Insert(Path path, String argument) {
        super(path, argument);
    }

    @Override
    public String execute() throws Exception {
        // Make sure path is not null
        if (path == null)
            throw new Exception("Path must not be null.");

        // Make sure path does not exist already
        if (path.exists())
            throw new Exception("Path already exists.");

        // Insert path
        path.insert();

        // Return success
        return null;
    }
}
