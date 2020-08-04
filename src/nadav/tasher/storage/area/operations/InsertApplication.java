package nadav.tasher.storage.area.operations;

import nadav.tasher.storage.area.Application;
import nadav.tasher.storage.implementation.Operation;

public class InsertApplication extends Operation {

    private String application;

    public InsertApplication(String application) {
        // Initialize the operation
        super("Create a new application \"" + application + "\"");

        // Initialize the variables
        this.application = application;
    }

    @Override
    public String execute() throws Exception {
        // Create the application object
        Application application = new Application(this.application);

        // Make sure the application does not exist already
        if (application.exists())
            // Throw exception
            throw new Exception("Unable to create application. Application already exists.");

        // Create the application
        application.insert();

        // Return null
        return null;
    }
}
