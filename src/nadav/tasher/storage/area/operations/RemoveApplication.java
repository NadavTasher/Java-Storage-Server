package nadav.tasher.storage.area.operations;

import nadav.tasher.storage.area.Application;
import nadav.tasher.storage.implementation.Operation;

public class RemoveApplication extends Operation {

    private String application;

    public RemoveApplication(String application) {
        // Initialize the operation
        super("Delete an application \"" + application + "\"");

        // Initialize the variables
        this.application = application;
    }

    @Override
    public String execute() throws Exception {
        // Create the application object
        Application application = new Application(this.application);

        // Make sure the application does not exist already
        if (!application.exists())
            // Throw exception
            throw new Exception("Unable to delete application. Application does not exist.");

        // Create the application
        application.remove();

        // Return null
        return null;
    }
}
