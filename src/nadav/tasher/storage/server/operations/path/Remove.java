package nadav.tasher.storage.server.operations.path;

import nadav.tasher.storage.implementation.Operation;
import nadav.tasher.storage.implementation.Path;

public class Remove extends Operation {

    /**
     * Remove operation constructor.
     *
     * @param path     Path to remove
     * @param argument Argument to work with
     */
    public Remove(Path path, String argument) {
        super(path, argument);
    }

    @Override
    public String execute() throws Exception {
        // Make sure path is not null
        if (path == null)
            throw new Exception("Path must not be null.");

        // Make sure path does not exist already
        if (!path.exists())
            throw new Exception("Path does not exist.");

        // Remove path
        path.remove();

        // Return success
        return "OK";
    }
}
