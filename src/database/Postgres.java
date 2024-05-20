package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Postgres {

    private static Postgres instance;
    private static final String DRIVER_CLASS = "org.postgresql.Driver";
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/guild_master";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "root";
    private Connection connection;

    private Postgres() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_CLASS);
    }

    public static synchronized Postgres getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new Postgres();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        return connection;
    }
}
