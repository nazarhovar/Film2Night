package Daos.Impl;

import Connections.ConnectorToDB;
import Daos.GenreDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class GenreDaoImpl implements GenreDao {
  public Integer getGenreIdByName(String genreName, Connection connection) throws SQLException {
    String query = "SELECT id FROM genre WHERE name = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setString(1, genreName);
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
        return resultSet.getInt("id");
    }
    return null;
}

public void linkFilmWithGenres(int filmId, Set<String> genres, Connection connection) throws SQLException {
    if (genres != null && !genres.isEmpty()) {
        String query = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        for (String genre : genres) {
            String[] genreNames = genre.split(",");
            for (String genreName : genreNames) {
                genreName = genreName.trim();
                Integer genreId = getGenreIdByName(genreName, connection);
                if (genreId != null) {
                    statement.setInt(1, filmId);
                    statement.setInt(2, genreId);
                    statement.executeUpdate();
                }
            }
        }
    }
}

public Set<String> getGenresByFilmId(int filmId) throws SQLException, ClassNotFoundException {
    Set<String> genres = new HashSet<>();
    Connection connection = ConnectorToDB.getConnection();
    String query = "SELECT genre.name FROM genre " +
            "JOIN film_genre ON film_genre.genre_id = genre.id " +
            "WHERE film_genre.film_id = ?";

    PreparedStatement statement = connection.prepareStatement(query);
    statement.setInt(1, filmId);
    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
        String genreName = resultSet.getString("name");
        genres.add(genreName);
    }
    return genres;
}

}
