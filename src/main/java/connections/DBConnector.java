package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector implements Connector {
    private static DBConnector instance;
    private static final String DB_URL = "jdbc:mysql://film2nightv2-my-web-app-db-1:3306/Film2NightMySQL2";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public DBConnector() {}

    public static DBConnector getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new DBConnector();
        }
        else if (instance.getConnection().isClosed()) {
            instance = new DBConnector();
        }

        return instance;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
