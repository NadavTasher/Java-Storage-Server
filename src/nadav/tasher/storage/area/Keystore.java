package nadav.tasher.storage.area;

import nadav.tasher.storage.implementation.Path;

import java.io.File;
import java.nio.file.Files;

public class Keystore extends Path {

    /**
     * Keystore constructor.
     *
     * @param area Parent area
     * @param name Keystore name
     */
    public Keystore(Area area, String name) {
        // Initialize path
        super(area.getDirectory(), name);
    }

    /**
     * Creates an entry object.
     *
     * @param id Entry ID
     * @return Entry object
     */
    public Entry entry(String id) {
        return new Entry(this, id);
    }

    private static class Entry extends Path {

        /**
         * Entry constructor.
         *
         * @param keystore Parent keystore
         * @param id       Entry ID
         */
        private Entry(Keystore keystore, String id) {
            // Initialize path
            super(keystore.getDirectory(), id);
        }

        /**
         * Creates a key object.
         *
         * @param name Key name
         * @return Key object
         */
        public Key key(String name) {
            return new Key(this, name);
        }

        private static class Key extends Path {

            // The entry's files
            private File binary;
            private File checksum;

            /**
             * Key constructor.
             *
             * @param entry Parent entry
             * @param name  Key name
             */
            private Key(Entry entry, String name) {
                // Initialize path
                super(entry.getDirectory(), name);

                // Initialize files
                this.binary = new File(this.getDirectory(), "binary");
                this.checksum = new File(this.getDirectory(), "checksum");
            }

            /**
             * Reads the key's value.
             *
             * @return Key value
             * @throws Exception Exception
             */
            public String get() throws Exception {
                // Make sure the binary file exists
                if (!this.binary.exists())
                    this.set(new String());

                // Read the file
                return new String(Files.readAllBytes(this.binary.toPath()));
            }

            /**
             * Writes the key's value.
             *
             * @param contents Key value
             * @throws Exception Exception
             */
            public void set(String contents) throws Exception {
                // Make sure the binary file exists
                if (!this.binary.exists())
                    // Create binary file
                    if (!this.binary.createNewFile())
                        // Throw exception
                        throw new Exception("Unable to create binary file. Check your permissions.");

                // Write contents to file
                Files.writeString(this.binary.toPath(), contents);
            }
        }
    }
}
