package nadav.tasher.storage.operations.area;

import nadav.tasher.storage.area.Area;
import nadav.tasher.storage.implementation.Operation;

public class Create extends Operation {

    private String area;

    public Create(String area) {
        // Initialize the operation
        super("Create a new area \"" + area + "\"");

        // Initialize the variables
        this.area = area;
    }

    @Override
    public String execute() throws Exception {
        // Create the area object
        Area area = new Area(this.area);

        // Make sure the area does not exist already
        if (area.exists())
            // Throw exception
            throw new Exception("Unable to create area. Area already exists.");

        // Create the area
        area.insert();

        // Return null
        return null;
    }
}
