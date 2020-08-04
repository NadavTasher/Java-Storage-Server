package nadav.tasher.storage.operations.table.entry;

import nadav.tasher.storage.area.Area;
import nadav.tasher.storage.implementation.Operation;

public class Delete extends Operation {

    private String area, keystore;

    public Delete(String area, String keystore) {
        // Initialize the operation
        super("Delete a keystore \"" + keystore + "\" in area \"" + area + "\"");

        // Initialize the variables
        this.area = area;
        this.keystore = keystore;
    }

    @Override
    public String execute() throws Exception {
        // Create the area object
        Area area = new Area(this.area);

        // Make sure the area exists
        if (!area.exists())
            // Throw exception
            throw new Exception("Unable to delete keystore. Area does not exist.");

        // Create the keystore object
        Area.Table table = area.table(this.keystore);

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