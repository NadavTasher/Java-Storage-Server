package nadav.tasher.storage.implementation;

public class Operation {

    protected final Path path;
    protected final String argument;

    /**
     * Operation constructor.
     * @param path Path to operate on
     * @param argument Argument to work with
     */
    public Operation(Path path, String argument){
        this.path = path;
        this.argument = argument;
    }

    /**
     * Executes the operation.
     *
     * @return Operation result
     * @throws Exception Exception
     */
    public String execute() throws Exception {
        return null;
    }

}
