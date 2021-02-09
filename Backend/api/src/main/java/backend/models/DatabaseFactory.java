package backend.models;

/*
 * Factory design pattern to enforce singleton on Database so only one connection to cloud database is ever made.
*/
public class DatabaseFactory {
    private static Database database = null;

    public static Database instance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }
}
