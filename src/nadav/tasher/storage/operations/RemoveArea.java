package nadav.tasher.storage.operations;

import nadav.tasher.storage.area.Area;
import nadav.tasher.storage.implementation.Operation;

public class RemoveArea extends Operation {

    private String area;

    public RemoveArea(String area) {
        // Initialize the operation
        super("Delete an area \"" + area + "\"");

        // Initialize the variables
        this.area = area;
    }

    @Override
    public String execute() throws Exception {
        // Create the area object
        Area area = new Area(this.area);

        // Make sure the area does not exist already
        if (!area.exists())
            // Throw exception
            throw new Exception("Unable to delete area. Area does not exist.");

        // Create the area
        area.remove();

        // Return null
        return null;
    }
}
