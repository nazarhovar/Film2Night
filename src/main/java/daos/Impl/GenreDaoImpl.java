package daos.Impl;

import connections.DBConnector;
import daos.GenreDao;
import entities.Genre;

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

    DBConnector dbConnector = DBConnector.getInstance();

    public GenreDaoImpl() throws SQLException, ClassNotFoundException {
    }

    public Map<String, Integer> getGenreIdByName(List<String> genreNames, Connection connection) throws SQLException {
        Map<String, Integer> genreIdMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ID)) {
            String genreNameString = String.join(",", genreNames);
            statement.setString(1, genreNameString);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    genreIdMap.put(resultSet.getString("name"), resultSet.getInt("id"));
                }
            }
        }

        return genreIdMap;
    }

    public void linkFilmWithGenres(int filmId, Set<Genre> genres, Connection connection) throws SQLException {
        if (genres == null || genres.isEmpty()) {
            return;
        }

        PreparedStatement statement = connection.prepareStatement(INSERT);

        genres.stream()
                .map(Genre::getName)
                .forEach(genreName -> {
                    try {
                        Map<String, Integer> genreIdMap = getGenreIdByName(Collections.singletonList(genreName),
                                connection);
                        Integer genreId = genreIdMap.get(genreName);
                        if (genreId != null) {
                            statement.setInt(1, filmId);
                            statement.setInt(2, genreId);
                            statement.addBatch();
                        }
                    } catch (SQLException e) {
                        System.out.println("Error linking film with genres: " + e.getMessage());
                    }
                });

        statement.executeBatch();
    }

    public Set<Genre> getGenresByFilmId(int filmId) throws SQLException, ClassNotFoundException {
        Set<Genre> genres = new HashSet<>();
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_GENRE)) {
            statement.setInt(1, filmId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Genre genre = new Genre();
                    genre.setName(resultSet.getString("name"));
                    genres.add(genre);
                }
            }
        }

        return genres;
    }
}
