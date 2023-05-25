package Daos;
import Entities.Top250;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Top250Dao {
    private Connection connection;

    public Top250Dao(Connection connection) {
        this.connection = connection;
    }

    public static List<Top250> getTop250() throws SQLException {
        List<Top250> topFilms = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost;database=Film2Night", "", "");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TOP_250");
            if (resultSet.next()) {
                topFilms.add(new Top250(resultSet));
            }
        }
        return topFilms;
    }

    public static void addTop250(Top250 topFilm) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost;database=Film2Night", "", "");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TOP_250 (id,name) VALUES (?, ?)")) {
            preparedStatement.setInt(1, topFilm.getId());
            preparedStatement.setString(2, topFilm.getName());
            preparedStatement.executeUpdate();
        }
    }

    public void updateTop250(Top250 topFilm) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost;database=Film2Night", "", "");
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE TOP_250 SET name=? WHERE id=?")) {
            preparedStatement.setInt(1, topFilm.getId());
            preparedStatement.setString(2, topFilm.getName());
            preparedStatement.executeUpdate();
        }
    }

    private Top250 resultSetToTop250(ResultSet resultSet) throws SQLException {
        Top250 topFilm = new Top250();
        topFilm.setId(resultSet.getInt("id"));
        topFilm.setName(resultSet.getString("name"));
        return topFilm;
    }

}

