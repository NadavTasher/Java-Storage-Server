package nadav.tasher.storage.server.operations.cell;

import nadav.tasher.storage.implementation.Application;
import nadav.tasher.storage.implementation.Operation;
import nadav.tasher.storage.implementation.Path;

public class Read extends Operation {

    /**
     * Read operation constructor.
     *
     * @param path     Path to read
     * @param argument Argument to default to
     */
    public Read(Path path, String argument) {
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

        // Read path
        return ((Application.Table.Row.Cell) path).read();
    }
}
