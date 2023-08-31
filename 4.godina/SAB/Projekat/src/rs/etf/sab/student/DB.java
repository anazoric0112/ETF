package rs.etf.sab.student;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {

    private static final String username = "users";
    private static final String password = "54321Trewq.";
    private static final String database = "Sales";
    private static final int port = 1433;
    private static final String server = "localhost";

    private static final String connectionUrl
            = "jdbc:sqlserver://" + server + ":" + port
            + ";databaseName=" + database
            + ";encrypt=true"
            + ";trustServerCertificate=true";

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private DB() {
        try {
            connection = DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static DB db = null;

    public static DB getInstance() {
        if (db == null) {
            db = new DB();
        }
        return db;
    }
}
