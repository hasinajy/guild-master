package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.ConfigLoader;

public class Postgres {
    private static final String CONFIG_FILE = "webapps/guild-master/WEB-INF/db-config.xml";
    private static Configuration config;
    private static final Logger logger = Logger.getLogger(Postgres.class.getName());
    private static Postgres instance = new Postgres();

    static {
        try {
            config = ConfigLoader.loadConfiguration(CONFIG_FILE);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load database configuration", e);
        }
    }

    public static synchronized Postgres getInstance() {
        if (instance == null) {
            instance = new Postgres();
        }

        return instance;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(
                config.getDatabaseUrl(),
                config.getUsername(),
                config.getPassword());
    }
}
