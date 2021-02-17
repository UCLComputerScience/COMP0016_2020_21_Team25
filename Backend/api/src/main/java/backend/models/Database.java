package backend.models;

import backend.ApiLogger;

import java.sql.*;

/*
 * Wrapper class to abstract database operation from the rest of the backend.
 */
public class Database {
    private final String urlPrefix = "jdbc:db2:";
    private Connection connection;
    private Statement statement;

    public Database() {
        try {
            connect();
            ApiLogger.log("Connected to database");
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load JDBC driver");
            System.err.println("Exception: " + e);
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Could not connect to database");
            System.err.println("Exception: " + e);
            e.printStackTrace();
        }
    }

    /**
     * @throws ClassNotFoundException if the maven driver package is not included.
     * @throws SQLException           if the connection to the cloud database fails.
     */
    private void connect() throws ClassNotFoundException, SQLException {
        String URL = urlPrefix
                + "//dashdb-txn-sbox-yp-dal09-11.services.dal.bluemix.net:50001/BLUDB:user=USERNAME;password=PASSWORD;sslConnection=true;";
        String username = "tzx29081";
        String password = "k0fnvr9nws+s6dcj";
        URL = URL.replace("USERNAME", username).replace("PASSWORD", password);
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        connection = DriverManager.getConnection(URL);
        // Do not autosave on command execution.
        connection.setAutoCommit(false);
        // Create statement object for executing SQL statements.
        statement = connection.createStatement();
    }

    /**
     * Run SQL query - no different to execute, just provides better semantics.
     */
    public ResultSet query(String query) {
        return executeStatement(query);
    }

    /**
     * Execute SQL statement - just to provide better semantics.
     */
    public void execute(String command) {
        executeStatement(command);
    }

    /**
     * @param command the statement to execute.
     * @return ResultSet the result of the SQL operation.
     */
    private ResultSet executeStatement(String command) {
        try {
            ApiLogger.log("Executing SQL Statement:\n" + command);
            ResultSet result = statement.executeQuery(command);
            // Save changes to database
            connection.commit();
            return result;
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                System.err.println("SQLSTATE: " + ex.getSQLState());
                System.err.println("Error code: " + ex.getErrorCode());
                ex.printStackTrace();
                ex = ex.getNextException(); // For drivers that support chained exceptions
            }
        }
        return null;
    }

    public void close() {
        try {
            statement.close();
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException ignored) {

        }
    }

}
