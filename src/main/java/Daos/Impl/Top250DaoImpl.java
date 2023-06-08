package Daos.Impl;
import Connections.ConnectorToDB;
import Daos.Top250Dao;
import Entities.Top250;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Top250DaoImpl implements Top250Dao {

    public List<Top250> getTop250() throws SQLException {
        List<Top250> topFilms = new ArrayList<>();
        try (Connection connection = ConnectorToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM top250");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Top250 top250 = new Top250();
                top250.setId(resultSet.getInt("id"));
                top250.setName(resultSet.getString("name"));
                topFilms.add(top250);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return topFilms;
    }

    public void addTop250(List<Top250> topFilms) throws SQLException {
        try (Connection connection = ConnectorToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Top250 (id, name) VALUES (?, ?)")) {
            for (Top250 topFilm : topFilms) {
                statement.setInt(1, topFilm.getId());
                statement.setString(2, topFilm.getName());
                statement.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Top250 saved to database");
    }
}

