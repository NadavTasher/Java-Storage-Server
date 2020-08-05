package nadav.tasher.storage.server.operations.cell;

import nadav.tasher.storage.implementation.Application;
import nadav.tasher.storage.implementation.Operation;
import nadav.tasher.storage.implementation.Path;

public class Write extends Operation {

    /**
     * Write operation constructor.
     *
     * @param path     Path to write
     * @param argument String to write
     */
    public Write(Path path, String argument) {
        super(path, argument);
    }

    @Override
    public String execute() throws Exception {
        // Make sure path is not null
        if (path == null)
            throw new Exception("Path must not be null.");

        // Make sure path exists
        if (!path.exists())
            throw new Exception("Path does not exist.");

        // Make sure path type is cell
        if (!(path instanceof Application.Table.Row.Cell))
            throw new Exception("Path is not a cell.");

        // Make sure argument is not null
        if (argument == null)
            throw new Exception("Argument must not be null.");

        // Write path
        ((Application.Table.Row.Cell) path).write(argument);

        // Return success
        return null;
    }
}
