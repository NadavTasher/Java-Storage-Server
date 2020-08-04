package nadav.tasher.storage.communication;

import nadav.tasher.storage.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CommandLine {

    // The server socket which accepts new clients
    private static ServerSocket socket;

    private static ArrayList<CommandLine.Client> clients;

    public static void initialize(int port) throws IOException {
        // Initialize the server socket
        CommandLine.socket = new ServerSocket(port);

        // Initialize the client array
        CommandLine.clients = new ArrayList<>();

        // Listen for clients
        while (Server.isRunning()) {
            // Check if a new client is connecting
            Socket socket = CommandLine.socket.accept();
            // Create a new client
            CommandLine.clients.add(new CommandLine.Client(socket));
        }
    }

    private static class Client {

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
            listen();
        }

        private void listen() {
            // Start a new thread
            new Thread(new Runnable() {
                @Override
                public void run() {
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
                Context context = new Context(parts[1]);

                // Check number of parts
                if (parts.length == 3) {
                    // Store value
                    String value = parts[2];
                } else {
                    switch (command.toLowerCase()) {
                        case "insert": {
                            // Check context type
                            switch (context.type){
                                case Area -> Server.
                            }
                            break;
                        }
                    }
                }
            }
        }

        private void prompt() {
            this.write("> ", false);
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

        private static boolean match(String[] parts, int index, String expected) {
            return parts[index].toLowerCase().equals(expected.toLowerCase());
        }

        private static class Context {

            private Type type;

            private String area, table, entry, value;

            /**
             * Context constructor.
             *
             * @param context Context string
             */
            private Context(String context) {
                // Split the context into parts
                String[] parts = context.split(":");
                // Check length of parts
                if (parts.length >= 1) {
                    // Create new context with type area
                    this.type = Type.Area;
                    // Set area
                    this.area = parts[0];
                }
                if (parts.length >= 2) {
                    // Create new context with type area
                    this.type = Type.Table;
                    // Set area
                    this.table = parts[1];
                }
                if (parts.length >= 3) {
                    // Create new context with type area
                    this.type = Type.Entry;
                    // Set area
                    this.entry = parts[2];
                }
                if (parts.length >= 4) {
                    // Create new context with type area
                    this.type = Type.Value;
                    // Set area
                    this.value = parts[3];
                }
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

            private static enum Type {
                Area,
                Table,
                Entry,
                Value
            }
        }
    }

}
