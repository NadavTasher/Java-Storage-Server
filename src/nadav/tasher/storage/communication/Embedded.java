package nadav.tasher.storage.communication;

import nadav.tasher.storage.server.Server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public abstract class Embedded {

    // The server socket which accepts new clients
    private static ServerSocket socket;

    private static ArrayList<Client> clients;

    public static void initialize(int port) throws IOException {
        // Initialize the server socket
        Embedded.socket = new ServerSocket(port);

        // Initialize the client array
        Embedded.clients = new ArrayList<>();

        // Listen for clients
        while (Server.isRunning()) {
            // Check if a new client is connecting
            Socket socket = Embedded.socket.accept();
            // Create a new client
            Embedded.clients.add(new Client(socket));
        }
    }

    private static class Client {

        private Socket socket;

        private InputStreamReader reader;
        private OutputStreamWriter writer;

        private Client(Socket socket){

        }
    }

}
