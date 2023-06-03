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
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TOP_250");
            if (resultSet.next()) {
                topFilms.add(new Top250(resultSet));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return topFilms;
    }

    public void addTop250(Top250 topFilm) throws SQLException {
        try (Connection connection = ConnectorToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TOP_250 (id,name) VALUES (?, ?)")) {
            preparedStatement.setInt(1, topFilm.getId());
            preparedStatement.setString(2, topFilm.getName());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

