package nadav.tasher.storage.communication;

import nadav.tasher.storage.area.Application;
import nadav.tasher.storage.area.operations.InsertApplication;
import nadav.tasher.storage.area.operations.InsertTable;
import nadav.tasher.storage.area.operations.RemoveApplication;
import nadav.tasher.storage.area.operations.RemoveTable;
import nadav.tasher.storage.implementation.Path;
import nadav.tasher.storage.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public abstract class Embedded {

    // The server socket which accepts new clients
    private static ServerSocket socket;

    private static ArrayList<Embedded.Client> clients;

    public static void initialize(int port) throws IOException {
        // Initialize the server socket
        Embedded.socket = new ServerSocket(port);

        // Initialize the client array
        Embedded.clients = new ArrayList<>();

        // Listen for clients
        while (Server.running) {
            // Check if a new client is connecting
            Socket socket = Embedded.socket.accept();
            // Create a new client
            Embedded.clients.add(new Embedded.Client(socket));
        }
    }

    private static class Client implements Server.Callback {

        private Socket socket;

        private BufferedReader reader;
        private BufferedWriter writer;

        private Client(Socket socket) throws IOException {
            // Initialize socket
            this.socket = socket;

            // Initialize I/O
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

            // Start listening
            this.listen();
        }

        private void listen() {
            // Start a new thread
            new Thread(() -> {
                try {
                    // Write a prompt
                    prompt();
                    // Handle data
                    while (socket.isConnected()) {
                        // Wait for an input
                        String input = reader.readLine();
                        // Handle input
                        handle(input);
                        // Write prompt
                        prompt();
                    }
                } catch (Exception e) {
                    // Finish connection
                }
            }).start();
        }

        private void handle(String input) {
            // Split input to parts divided by a whitespace
            String[] parts = input.split(" ", 3);

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
                            p
                            break;
                        }
                        case "remove": {
                            // Check context type
                            switch (context.type) {
                                case Application -> Server.execute(new RemoveApplication(context.application), this);
                                case Table -> Server.execute(new RemoveTable(context.application, context.table), this);
                                case Row -> Server.execute(new InsertEntry(context.application, context.table, context.row), this);
                                case Column -> Server.execute(new InsertValue(context.application, context.table, context.row, context.column), this);
                            }
                            break;
                        }
                        case "exists": {
                            // Check context type
                            switch (context.type) {
                                case Application -> Server.execute(new RemoveApplication(context.application), this);
                                case Table -> Server.execute(new RemoveTable(context.application, context.table), this);
                                case Row -> Server.execute(new InsertEntry(context.application, context.table, context.row), this);
                                case Column -> Server.execute(new InsertValue(context.application, context.table, context.row, context.column), this);
                            }
                            break;
                        }
                        case "set": {
                            // Check context type
                            switch (context.type) {
                                case Application -> Server.execute(new RemoveApplication(context.application), this);
                                case Table -> Server.execute(new RemoveTable(context.application, context.table), this);
                                case Row -> Server.execute(new InsertEntry(context.application, context.table, context.row), this);
                                case Column -> Server.execute(new InsertValue(context.application, context.table, context.row, context.column), this);
                            }
                            break;
                        }
                    }
                }
            }
        }

        private void write(String string, boolean newLine) {
            try {
                // Write string
                this.writer.write(string);

                // Check new line
                if (newLine)
                    // Write new line
                    this.writer.newLine();

                // Flush output
                this.writer.flush();
            } catch (Exception ignored) {
            }
        }

        @Override
        public void success(String result) {
            // Write result to output
            this.writer.write("true:");
        }

        @Override
        public void failure(Exception exception) {

        }
    }
}
