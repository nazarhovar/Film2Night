package daos.Impl;
import connections.DBConnector;
import daos.Top250Dao;
import entities.Top250;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Top250DaoImpl implements Top250Dao {

    private static final String SELECT = "SELECT * FROM Top250";
    private static final String INSERT = "INSERT INTO Top250 (id, name) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM Top250";
    private static final String UPDATE = "UPDATE Top250 SET name = ? WHERE id = ?";

    DBConnector dbConnector = DBConnector.getInstance();

    public Top250DaoImpl() throws SQLException, ClassNotFoundException {
    }

    public List<Top250> getTop250() throws SQLException {
        List<Top250> topFilms = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Top250 top250 = new Top250();
                top250.setId(resultSet.getInt("id"));
                top250.setName(resultSet.getString("name"));
                topFilms.add(top250);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error retrieving Top250 films from database: " + e.getMessage());
        }

        return topFilms;
    }

    public void addTop250(List<Top250> topFilms) throws SQLException {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            for (Top250 topFilm : topFilms) {
                statement.setInt(1, topFilm.getId());
                statement.setString(2, topFilm.getName());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (ClassNotFoundException e) {
            System.out.println("Error adding Top250 films to database: " + e.getMessage());
        }
        System.out.println("Top250 saved to database");
    }

    public void updateTop250(Top250 top250) throws SQLException {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, top250.getName());
            statement.setInt(2, top250.getId());
            statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            System.out.println("Error updating film in database: " + e.getMessage());
        }
        System.out.println("Film updated in database: " + top250.getName());
    }

    public void deleteTop250() throws SQLException {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            System.out.println("Error deleting Top250 films from database: " + e.getMessage());
        }
    }
}

