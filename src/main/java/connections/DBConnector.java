package connections;

import util.PropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector implements Connector {
    private static DBConnector instance;
    private static final String CONFIG_FILE = "/app/web/db.properties";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private final Properties dbProperties;

    private DBConnector() {
        dbProperties = PropertiesReader.loadProperties(CONFIG_FILE);
    }

    public static DBConnector getInstance() {
        if (instance == null) {
            instance = new DBConnector();
        }

        return instance;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String dbUrl = dbProperties.getProperty(DB_URL);
        String dbUser = dbProperties.getProperty(DB_USER);
        String dbPassword = dbProperties.getProperty(DB_PASSWORD);

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
