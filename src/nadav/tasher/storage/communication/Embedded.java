package nadav.tasher.storage.communication;

import nadav.tasher.storage.area.Application;
import nadav.tasher.storage.area.operations.InsertApplication;
import nadav.tasher.storage.area.operations.InsertTable;
import nadav.tasher.storage.area.operations.RemoveApplication;
import nadav.tasher.storage.area.operations.RemoveTable;
import nadav.tasher.storage.implementation.Operation;
import nadav.tasher.storage.implementation.Path;
import nadav.tasher.storage.server.Server;
import nadav.tasher.storage.system.Utility;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public abstract class Embedded {

    // The server socket which accepts new clients
    private static ServerSocket socket;

    public static void initialize(int port) throws IOException {
        // Initialize the server socket
        Embedded.socket = new ServerSocket(port);

        // Create embedded thread
        Thread embedded = new Thread(() -> {
            // Handle client forever
            try {
                while (true)
                    Embedded.handle(Embedded.socket.accept());
            } catch (Exception ignored) {
            }
        });

        // Start embedded thread
        embedded.start();
    }

    private static void handle(Socket socket) {
        // Create client thread
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create buffered I/O
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    // Create a callback
                    Server.Callback callback = new Server.Callback() {
                        @Override
                        public void success(String result) {
                            try {
                                writer.write("+ " + result + "\n");
                                writer.flush();
                            } catch (Exception ignored) {
                            }
                        }

                        @Override
                        public void failure(Exception exception) {
                            try {
                                writer.write("- " + exception.getMessage() + "\n");
                                writer.flush();
                            } catch (Exception ignored) {
                            }
                        }
                    };

                    // Listen for input forever
                    while (true) {
                        // Split input to parts divided by a whitespace
                        String[] parts = reader.readLine().split(" ", 3);

                        // Check number of parts
                        if (parts.length >= 2) {
                            // Store command
                            String command = parts[0];

                            // Create context
                            Path path = Path.fromString(parts[1]);

                            // Parse path type
                            String type = path.getClass().getSimpleName().toLowerCase();

                            switch (command.toLowerCase()) {
                                case "insert": {
                                    Server.execute(new Operation("Insert new " + type) {
                                        @Override
                                        public String execute() throws Exception {
                                            // Insert path
                                            path.insert();
                                            // Return success
                                            return "Inserted " + type;
                                        }
                                    }, callback);
                                    break;
                                }
                                case "remove": {
                                    Server.execute(new Operation("Remove " + type) {
                                        @Override
                                        public String execute() throws Exception {
                                            // Remove path
                                            path.remove();
                                            // Return success
                                            return "Removed " + type;
                                        }
                                    }, callback);
                                    break;
                                }
                                case "exists": {
                                    Server.execute(new Operation("Check " + type) {
                                        @Override
                                        public String execute() throws Exception {
                                            // Check path
                                            return String.valueOf(path.exists());
                                        }
                                    }, callback);
                                    break;
                                }
                                case "value": {
                                    if (parts.length == 3) {
                                        Server.execute(new Operation("Write " + type) {
                                            @Override
                                            public String execute() throws Exception {
                                                // Check type
                                                if (!(path instanceof Application.Table.Row.Column))
                                                    throw new Exception("Unable to write to type " + type + ".");

                                                // Write column
                                                ((Application.Table.Row.Column) path).set(parts[2]);

                                                // Return success
                                                return "OK";
                                            }
                                        }, callback);
                                    } else {
                                        Server.execute(new Operation("Read " + type) {
                                            @Override
                                            public String execute() throws Exception {
                                                // Check type
                                                if (!(path instanceof Application.Table.Row.Column))
                                                    throw new Exception("Unable to read from type " + type + ".");

                                                // Read column
                                                return ((Application.Table.Row.Column) path).get();
                                            }
                                        }, callback);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        });

        // Set daemon
        client.setDaemon(true);

        // Start client thread
        client.start();
    }
}
