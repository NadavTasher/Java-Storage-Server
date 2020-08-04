package nadav.tasher.storage.communication;

import nadav.tasher.storage.area.Application;
import nadav.tasher.storage.area.operations.InsertApplication;
import nadav.tasher.storage.area.operations.InsertTable;
import nadav.tasher.storage.area.operations.RemoveApplication;
import nadav.tasher.storage.area.operations.RemoveTable;
import nadav.tasher.storage.implementation.Operation;
import nadav.tasher.storage.implementation.Path;
import nadav.tasher.storage.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Embedded {

    // The server socket which accepts new clients
    private static ServerSocket socket;

    public static void initialize(int port) throws IOException {
        // Initialize the server socket
        Embedded.socket = new ServerSocket(port);

        // Create a timer
        Timer embedded = new Timer();

        // Schedule a task
        embedded.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Embedded.handle(Embedded.socket.accept());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10);
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
                                writer.write("+" + result + "\n");
                                writer.flush();
                            } catch (Exception ignored) {
                            }
                        }

                        @Override
                        public void failure(Exception exception) {
                            try {
                                writer.write("-" + exception.getMessage() + "\n");
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

                            // Check number of parts
                            if (parts.length == 3) {
                                // Store value
                                String data = parts[2];
                            } else {
                                switch (command.toLowerCase()) {
                                    case "insert": {
                                        // Check path type
                                        Server.execute(new Operation("Insert a new path") {
                                            @Override
                                            public String execute() throws Exception {
                                                path.insert();
                                                return null;
                                            }
                                        }, callback);
                                        break;
                                    }
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
