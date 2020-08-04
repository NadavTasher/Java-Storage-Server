package nadav.tasher.storage.area;

import nadav.tasher.storage.implementation.Path;
import nadav.tasher.storage.system.Storage;

import java.io.File;
import java.nio.file.Files;

public class Application extends Path {

    // Keystores
    private Table configuration;

    /**
     * Application constructor.
     *
     * @param name Application name
     */
    public Application(String name) {
        // Initialize path
        super(Storage.ROOT, name);

        // Initialize the configuration keystore
        this.configuration = new Table(this, "configuration");
    }

    /**
     * Creates a keystore object.
     *
     * @param name Keystore name
     * @return Keystore object
     */
    public Table table(String name) {
        // Create the keystore object
        return new Table(this, name);
    }

    public static class Table extends Path {

        /**
         * Table constructor.
         *
         * @param application Parent application
         * @param name        Table name
         */
        private Table(Application application, String name) {
            // Initialize path
            super(application.getDirectory(), name);
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

        public static class Entry extends Path {

            /**
             * Entry constructor.
             *
             * @param table Parent keystore
             * @param id    Entry ID
             */
            private Entry(Table table, String id) {
                // Initialize path
                super(table.getDirectory(), id);
            }

            /**
             * Creates a key object.
             *
             * @param name Key name
             * @return Key object
             */
            public Column column(String name) {
                return new Column(this, name);
            }

            public static class Column extends Path {

                // The entry's files
                private File binary;
                private File checksum;

                /**
                 * Column constructor.
                 *
                 * @param entry  Parent entry
                 * @param name Column name
                 */
                private Column(Entry entry, String name) {
                    // Initialize path
                    super(entry.getDirectory(), name);

                    // Initialize files
                    this.binary = new File(this.getDirectory(), "binary");
                    this.checksum = new File(this.getDirectory(), "checksum");
                }

                /**
                 * Reads the value.
                 *
                 * @return Column
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
                 * Writes the value.
                 *
                 * @param contents Column
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
}
