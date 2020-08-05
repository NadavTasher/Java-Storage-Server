package nadav.tasher.storage.communication;

import nadav.tasher.storage.implementation.Application;
import nadav.tasher.storage.implementation.Operation;
import nadav.tasher.storage.implementation.Path;
import nadav.tasher.storage.server.Server;
import nadav.tasher.storage.server.operations.cell.Read;
import nadav.tasher.storage.server.operations.cell.Write;
import nadav.tasher.storage.server.operations.path.Exists;
import nadav.tasher.storage.server.operations.path.Insert;
import nadav.tasher.storage.server.operations.path.Remove;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
                            write(result);
                        }

                        @Override
                        public void failure(Exception exception) {
                            write(exception);
                        }

                        public void write(Object message) {
                            try {
                                // Initialize prefix
                                String prefix = "?";

                                // Check message type
                                if (message instanceof Exception)
                                    prefix = "-";
                                if (message instanceof String)
                                    prefix = "+";

                                // Write message
                                writer.write(prefix);
                                writer.write(32);
                                writer.write(String.valueOf(message));
                                writer.write(10);

                                // Flush
                                writer.flush();
                            } catch (Exception ignored) {
                            }
                        }
                    };

                    // Listen for input forever
                    while (true) {
                        // Split input to parts divided by a whitespace
                        String[] slices = reader.readLine().split(" ", 3);

                        // Initialize variables
                        String command = null, path = null, argument = null;

                        // Check slices length
                        if (slices.length >= 1)
                            command = slices[0];

                        if (slices.length >= 2)
                            path = slices[1];

                        if (slices.length >= 3)
                            argument = slices[2];

                        // Copy to effective finals

                        // Make sure command is not null
                        if (command == null)
                            continue;

                        // Make sure path is not null
                        if (path == null)
                            continue;

                        // Parse path from string
                        Path mPath = Path.fromString(path);

                        // Switch between commands
                        if (command.equals("+")) {
                            // Insert a new insertion operation to the queue
                            Server.execute(new Insert(mPath, argument), callback);

                            // Continue
                            continue;
                        }
                        if (command.equals("-")) {
                            // Insert a new remove operation to the queue
                            Server.execute(new Remove(mPath, argument), callback);

                            // Continue
                            continue;
                        }
                        if (command.equals("?")) {
                            // Insert a new remove operation to the queue
                            Server.execute(new Exists(mPath, argument), callback);

                            // Continue
                            continue;
                        }
                        if (command.equals("<")) {
                            // Insert a new read operation to the queue
                            Server.execute(new Read(mPath, argument), callback);

                            // Continue
                            continue;
                        }
                        if (command.equals(">")) {
                            // Insert a new write operation to the queue
                            Server.execute(new Write(mPath, argument), callback);

                            // Continue
                            continue;
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        });

        // Start client thread
        client.start();
    }
}
