package nadav.tasher.storage.server;

import nadav.tasher.storage.implementation.Operation;
import nadav.tasher.storage.system.Utility;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class Server {

    private static Queue<Utility.Tuple<Operation, Callback>> queue;

    public static void initialize() {
        // Create queue
        Server.queue = new ArrayDeque<>();
        // Create server thread
        Thread server = new Thread(() -> {
            // Handle queue forever
            while (true)
                Server.handle();
        });

        // Start thread
        server.start();
    }

    /**
     * Creates a new execution entry in the server's queue.
     *
     * @param operation Operation to execute
     * @param callback  Callback to call
     */
    public static void execute(Operation operation, Callback callback) {
        Server.queue.add(new Utility.Tuple<>(operation, callback));
    }

    private static void handle() {
        // Check if queue has pending operations
        if (!Server.queue.isEmpty()) {
            // Pop the pending operation
            Utility.Tuple<Operation, Callback> entry = Server.queue.remove();

            // Make sure the entry is not null
            if (entry == null)
                return;

            // Make sure the operation is not null
            if (entry.getKey() == null)
                return;

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
