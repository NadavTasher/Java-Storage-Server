package nadav.tasher.storage;

import nadav.tasher.storage.communication.CommandLine;
import nadav.tasher.storage.communication.Embedded;
import nadav.tasher.storage.server.Server;
import nadav.tasher.storage.system.Storage;

public class Main {

    public static void main(String[] args) {

        try {
            // Initialize the storage manager
            Storage.initialize();
            // Initialize communications
            Embedded.initialize(1000);
            CommandLine.initialize(1001);
            // Initialize the server
            Server.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
