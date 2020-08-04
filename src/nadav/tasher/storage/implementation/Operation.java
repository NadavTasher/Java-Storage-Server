package nadav.tasher.storage.implementation;

public class Operation {

    protected String description;

    /**
     * Operation constructor.
     * @param description Description
     */
    protected Operation(String description) {
        this.description = description;
    }

    /**
     * Returns the description.
     * @return Description
     */
    public String getDescription() {
        return this.description;
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
