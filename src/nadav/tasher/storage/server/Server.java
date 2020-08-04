package nadav.tasher.storage.server;

import nadav.tasher.storage.area.Area;
import nadav.tasher.storage.implementation.Operation;
import nadav.tasher.storage.implementation.Tuple;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public abstract class Server {

    private static boolean running = true;

    private static final Queue<Tuple<Operation, Callback>> queue = new ArrayDeque<>();

    public static void initialize() {
        // Execute queue operations
        while (running) {
            // Check if queue has pending operations
            if (!Server.queue.isEmpty()) {
                // Pop the pending operation
                Tuple<Operation, Callback> entry = Server.queue.remove();

                // Make sure the entry is not null
                if (entry == null)
                    continue;

                // Make sure the operation is not null
                if (entry.getKey() == null)
                    continue;

                // Execute operation
                try {
                    // Execute
                    String result = entry.getKey().execute();

                    // Check if a callback is registered
                    if (entry.getValue() != null) {
                        entry.getValue().success(result);
                    }
                } catch (Exception exception) {
                    // Check if a callback is registered
                    if (entry.getValue() != null) {
                        entry.getValue().failure(exception);
                    }
                }
            }
        }
    }

    /**
     * Creates a new execution entry in the server's queue.
     *
     * @param operation Operation to execute
     * @param callback  Callback to call
     */
    public static void execute(Operation operation, Callback callback) {
        Server.queue.add(new Tuple<>(operation, callback));
    }

    /**
     * This interface is used to handle operation execution results.
     */
    public interface Callback {

        /**
         * Handles operation successes.
         *
         * @param result Result
         */
        void success(String result);

        /**
         * Handles operation failures.
         *
         * @param exception Thrown exception
         */
        void failure(Exception exception);
    }
}
