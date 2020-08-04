package nadav.tasher.storage.area.operations;

import nadav.tasher.storage.area.Application;
import nadav.tasher.storage.implementation.Operation;

public class InsertTable extends Operation {

    private String area, keystore;

    public InsertTable(String area, String keystore) {
        // Initialize the operation
        super("Create a new keystore \"" + keystore + "\" in area \"" + area + "\"");

        // Initialize the variables
        this.area = area;
        this.keystore = keystore;
    }

    @Override
    public String execute() throws Exception {
        // Create the application object
        Application application = new Application(this.area);

        // Make sure the application exists
        if (!application.exists())
            // Throw exception
            throw new Exception("Unable to create keystore. Application does not exist.");

        // Create the keystore object
        Application.Table table = application.table(this.keystore);

        // Make sure the keystore does not exists
        if (table.exists())
            // Throw exception
            throw new Exception("Unable to create keystore. Keystore already exists.");

        // Create the keystore
        table.insert();

        // Return null
        return null;
    }
}