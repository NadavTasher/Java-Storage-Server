package nadav.tasher.storage.operations.keystore;

import nadav.tasher.storage.area.Area;
import nadav.tasher.storage.implementation.Operation;

public class Create extends Operation {

    private String area, keystore;

    public Create(String area, String keystore) {
        // Initialize the operation
        super("Create a new keystore \"" + keystore + "\" in area \"" + area + "\"");

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
            throw new Exception("Unable to create keystore. Area does not exist.");

        // Create the keystore object
        Area.Keystore keystore = area.keystore(this.keystore);

        // Make sure the keystore does not exists
        if (keystore.exists())
            // Throw exception
            throw new Exception("Unable to create keystore. Keystore already exists.");

        // Create the keystore
        keystore.insert();

        // Return null
        return null;
    }
}