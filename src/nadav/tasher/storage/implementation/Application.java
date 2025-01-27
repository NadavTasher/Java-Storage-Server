package nadav.tasher.storage.implementation;

import nadav.tasher.storage.system.Storage;

import java.io.File;
import java.nio.file.Files;

public class Application extends Path {

    /**
     * Application constructor.
     *
     * @param name Application name
     */
    public Application(String name) {
        // Initialize path
        super(Storage.ROOT, name);
    }

    /**
     * Creates a table object.
     *
     * @param name Table name
     * @return Table object
     */
    public Table table(String name) {
        // Create the table object
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
         * Creates an row object.
         *
         * @param id Row ID
         * @return Row object
         */
        public Row row(String id) {
            return new Row(this, id);
        }

        public static class Row extends Path {

            /**
             * Row constructor.
             *
             * @param table Parent table
             * @param id    Row ID
             */
            private Row(Table table, String id) {
                // Initialize path
                super(table.getDirectory(), id);
            }

            /**
             * Creates a cell object.
             *
             * @param name Cell name
             * @return Cell object
             */
            public Cell cell(String name) {
                return new Cell(this, name);
            }

            public static class Cell extends Path {

                // The row's files
                private File binary;
                private File checksum;

                /**
                 * Cell constructor.
                 *
                 * @param row  Parent row
                 * @param name Cell name
                 */
                private Cell(Row row, String name) {
                    // Initialize path
                    super(row.getDirectory(), name);

                    // Initialize files
                    this.binary = new File(this.getDirectory(), "binary");
                    this.checksum = new File(this.getDirectory(), "checksum");
                }

                /**
                 * Reads the value.
                 *
                 * @return Cell
                 * @throws Exception Exception
                 */
                public String read() throws Exception {
                    // Make sure the binary file exists
                    if (!this.binary.exists())
                        this.write(new String());

                    // Read the file
                    return new String(Files.readAllBytes(this.binary.toPath()));
                }

                /**
                 * Writes the value.
                 *
                 * @param contents Cell
                 * @throws Exception Exception
                 */
                public void write(String contents) throws Exception {
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
