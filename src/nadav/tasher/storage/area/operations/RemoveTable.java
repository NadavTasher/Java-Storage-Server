package nadav.tasher.storage.area.operations;

import nadav.tasher.storage.area.Application;
import nadav.tasher.storage.implementation.Operation;

public class RemoveTable extends Operation {

    private String area, keystore;

    public RemoveTable(String area, String keystore) {
        // Initialize the operation
        super("Delete a keystore \"" + keystore + "\" in area \"" + area + "\"");

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
            throw new Exception("Unable to delete keystore. Application does not exist.");

        // Create the keystore object
        Application.Table table = application.table(this.keystore);

        // Make sure the keystore does not exists
        if (!table.exists())
            // Throw exception
            throw new Exception("Unable to delete keystore. Keystore does not exist.");

        // Create the keystore
        table.remove();

        // Return null
        return null;
    }
}