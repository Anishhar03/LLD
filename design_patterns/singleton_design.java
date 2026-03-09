package design_patterns;
/*
 * package:
 * - Used to group related classes together
 * - Avoids name conflicts
 * - Folder name MUST match the package name
 * - Best practice: use lowercase names
 */

// ==================================
// Singleton Design Pattern
// Database Connection Example
// ==================================

class DatabaseConnection {
    /*
     * class:
     * - Blueprint for creating objects
     * - This class represents a shared resource (database connection)
     * - Singleton is used because only ONE database connection should exist
     */

    // Step 1: Declare the single instance
    private static volatile DatabaseConnection instance;
    /*
     * private:
     * - Restricts access to this variable within the class only
     * - Prevents external modification of the instance
     *
     * static:
     * - Belongs to the class, not to objects
     * - Ensures only ONE copy exists in memory
     *
     * volatile:
     * - Ensures visibility of changes across threads
     * - Prevents JVM instruction reordering
     * - Avoids partially constructed objects in multithreading
     *
     * DatabaseConnection instance:
     * - Holds the single object of this class
     * - Initially null (lazy initialization)
     */

    // Step 2: Private constructor
    private DatabaseConnection() {
        /*
         * private constructor:
         * - Prevents object creation using 'new'
         * - Enforces Singleton rule (only one instance)
         */

        // Protection against reflection
        if (instance != null) {
            /*
             * Reflection can access private constructors
             * This check prevents creation of a second instance
             */
            throw new RuntimeException(
                "Use getInstance() method to get the single instance"
            );
        }

        // Just for demonstration to show constructor is called only once
        System.out.println("Database Connection Created");
    }

    // Step 3: Global access point to the instance
    public static DatabaseConnection getInstance() {
        /*
         * public:
         * - Accessible from anywhere
         *
         * static:
         * - Can be called without creating an object
         * - Example: DatabaseConnection.getInstance()
         *
         * Returns:
         * - The same DatabaseConnection instance every time
         */

        // First null check (for performance)
        if (instance == null) {
            /*
             * Avoids synchronization once the instance is created
             * Improves performance in repeated calls
             */

            // Synchronize only when instance is null
            synchronized (DatabaseConnection.class) {
                /*
                 * synchronized:
                 * - Ensures only one thread can execute this block at a time
                 *
                 * DatabaseConnection.class:
                 * - Class-level lock
                 * - Ensures thread safety across all threads
                 */

                // Second null check (double-checked locking)
                if (instance == null) {
                    /*
                     * Ensures only ONE object is created
                     * Even if multiple threads reach here
                     */
                    instance = new DatabaseConnection();
                }
            }
        }

        // Return the single instance
        return instance;
    }

    // Business method
    public void connect() {
        /*
         * Represents real functionality
         * Singleton is useful only when it performs shared operations
         */
        System.out.println("Connected to database");
    }
}

// ---------- CLIENT ----------
public class singleton_design {
    /*
     * public:
     * - Only one public class allowed per file
     * - File name must match class name (SingletonDemo.java)
     */

    public static void main(String[] args) {
        /*
         * public: JVM must access it
         * static: No object needed to call main
         * void: No return value
         * String[] args: Command-line arguments
         */

        // First call → instance is created
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        db1.connect();

        // Second call → same instance is returned
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        db2.connect();

        // Checks whether both references point to the same object
        System.out.println(db1 == db2); // true → confirms Singleton
    }
}
