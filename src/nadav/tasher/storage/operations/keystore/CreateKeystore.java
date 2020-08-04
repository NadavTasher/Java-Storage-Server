package nadav.tasher.storage.operations.keystore;

import nadav.tasher.storage.area.Area;
import nadav.tasher.storage.area.Keystore;
import nadav.tasher.storage.implementation.Operation;

public class CreateKeystore extends Operation {

    private String area, keystore;

    public CreateKeystore(String area, String keystore) {
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
        Keystore keystore = area.keystore(this.keystore);
        // Make sure the keystore does not exists


        // Create the area
        area.insert();

        // Return null
        return null;
    }
}