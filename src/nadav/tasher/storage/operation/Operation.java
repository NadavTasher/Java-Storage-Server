package nadav.tasher.storage.operation;

public interface Operation {

    /**
     * Returns a detailed description of the operation.
     *
     * @return Operation description
     */
    String description();

    /**
     * Executes the operation and returns a result as needed.
     *
     * @return Operation result
     * @throws OperationException Operation exception
     */
    String execute() throws OperationException;

    static class OperationException extends Exception {

    }
}
