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
            String[] parts = input.split(" ", 4);

            // Check number of parts
            if (parts.length >= 1) {
                // Store command
                String command = parts[0];

                // Check number of parts
                if (parts.length >= 2) {
                    // Store type
                    String type = parts[1];

                    // Check number of parts
                    if (parts.length >= 3) {
                    }
                }
                String type = parts[1];
                // Check first parameter
//                if ()

            } else {
                this.write("Missing arguments", true);
            }
            if ()
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
    }

}
