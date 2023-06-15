package Daos.Impl;

import Connections.ConnectorToDB;
import Daos.GenreDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GenreDaoImpl implements GenreDao {

    private static final String GET_ID = "SELECT id,name FROM genre WHERE name = ?";
    private static final String INSERT = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
    private static final String GET_GENRE = "SELECT genre.name FROM genre " +
            "INNER JOIN film_genre ON film_genre.genre_id = genre.id WHERE film_genre.film_id = ?";

    public Map<String, Integer> getGenreIdByName(List<String> genreNames, Connection connection) throws SQLException {
        Map<String, Integer> genreIdMap = new HashMap<>();
        PreparedStatement statement = connection.prepareStatement(GET_ID);

        String genreNameString = String.join(",", genreNames);
        statement.setString(1, genreNameString);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            genreIdMap.put(resultSet.getString("name"), resultSet.getInt("id"));
        }

        return genreIdMap;
    }

    public void linkFilmWithGenres(int filmId, Set<String> genres, Connection connection) throws SQLException {
        if (genres == null || genres.isEmpty()) {
            return;
        }

        PreparedStatement statement = connection.prepareStatement(INSERT);

        genres.stream()
                .flatMap(genre -> Arrays.stream(genre.split(",")))
                .map(String::trim)
                .forEach(genreName -> {
                    try {
                        Map<String, Integer> countryIdMap = getGenreIdByName(Collections.singletonList(genreName), connection);
                        Integer genreId = countryIdMap.get(genreName);
                        if (genreId != null) {
                            statement.setInt(1, filmId);
                            statement.setInt(2, genreId);
                            statement.addBatch();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

        statement.executeBatch();
    }

    public Set<String> getGenresByFilmId(int filmId) throws SQLException, ClassNotFoundException {
        Set<String> genres = new HashSet<>();
        Connection connection = ConnectorToDB.getConnection();

        PreparedStatement statement = connection.prepareStatement(GET_GENRE);
        statement.setInt(1, filmId);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            genres.add(resultSet.getString("name"));
        }

        return genres;
    }
}
