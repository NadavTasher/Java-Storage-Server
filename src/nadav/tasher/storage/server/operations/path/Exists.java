package nadav.tasher.storage.server.operations.path;

import nadav.tasher.storage.implementation.Operation;
import nadav.tasher.storage.implementation.Path;

public class Exists extends Operation {
    /**
     * Exists operation constructor.
     *
     * @param path     Path to check
     * @param argument Argument to work with
     */
    public Exists(Path path, String argument) {
        super(path, argument);
    }

    @Override
    public String execute() throws Exception {
        // Make sure path is not null
        if (path == null)
            throw new Exception("Path must not be null.");

        // Return success
        return String.valueOf(path.exists());
    }
}
