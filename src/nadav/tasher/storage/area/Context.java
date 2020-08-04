package nadav.tasher.storage.area;

public class Context {

    // The context's type
    private Type type;

    // The context's scopes
    private String area, table, entry, value;

    /**
     * Context constructor.
     */
    private Context() {
    }

    public String getArea() {
        return area;
    }

    public String getTable() {
        return table;
    }

    public String getEntry() {
        return entry;
    }

    public String getValue() {
        return value;
    }

    /**
     * Constructs a context from a string.
     *
     * @param string String
     * @return Context
     */
    public static Context fromString(String string) {
        // Create context
        Context context = new Context();

        // Split the context into parts
        String[] parts = string.split(":");

        // Check length of parts
        if (parts.length >= 1) {
            // Create new context with type area
            context.type = Type.Area;
            // Set area
            context.area = parts[0];
        }

        // Check length of parts
        if (parts.length >= 2) {
            // Create new context with type area
            context.type = Type.Table;
            // Set area
            context.table = parts[1];
        }

        // Check length of parts
        if (parts.length >= 3) {
            // Create new context with type area
            context.type = Type.Entry;
            // Set area
            context.entry = parts[2];
        }

        // Check length of parts
        if (parts.length >= 4) {
            // Create new context with type area
            context.type = Type.Value;
            // Set area
            context.value = parts[3];
        }

        // Return the context
        return context;
    }

    /**
     * Constructs a context from a JSON object.
     *
     * @param json JSON object
     * @return Context
     */
    public static Context fromJSON(JSONObject json) {
        // Parse
    }

    private enum Type {
        Area,
        Table,
        Entry,
        Value
    }
}