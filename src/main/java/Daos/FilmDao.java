package Daos;
import Entities.Film;

import java.sql.*;

public class FilmDao {
    private final Connection connection;

    public FilmDao(Connection connection) {
        this.connection = connection;
    }

    public static void addFilm(Film film) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Film2Night", "root", "password");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Film (kinopoiskId, nameOriginal, posterUrl, ratingKinopoisk, ratingKinopoiskVoteCount,webUrl,year,filmLength,countries,genres,lastSync,isBlocked) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, String.valueOf(film.getKinopoiskId()));
            statement.setString(2,film.getNameOriginal());
            statement.setString(3,film.getPosterUrl());
            statement.setString(4, String.valueOf(film.getRatingKinopoisk()));
            statement.setString(5, String.valueOf(film.getRatingKinopoiskVoteCount()));
            statement.setString(6,film.getWebUrl());
            statement.setString(7, String.valueOf(film.getYear()));
            statement.setString(8, String.valueOf(film.getFilmLength()));
            statement.setString(9, String.valueOf(film.getCountries()));
            statement.setString(10, String.valueOf(film.getGenres()));
            statement.setString(11, String.valueOf(film.getLastSync()));
            statement.setString(12,film.getIsBlocked());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteFilm(int id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Film2Night", "root", "password");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Film WHERE kinopoiskId = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Film getFilmById(int id) {
        Film film = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Film2Night", "root", "password");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Film WHERE kinopoiskId = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToFilm(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return film;
    }

    private static Film resultSetToFilm(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setKinopoiskId(resultSet.getInt("kinopoiskId"));
        film.setNameOriginal(resultSet.getString("nameOriginal"));
        film.setPosterUrl(resultSet.getString("posterUrl"));
        film.setRatingKinopoisk(resultSet.getFloat("ratingKinopoisk"));
        film.setRatingKinopoiskVoteCount(resultSet.getInt("ratingKinopoiskVoteCount"));
        film.setWebUrl(resultSet.getString("webUrl"));
        film.setYear(resultSet.getInt("year"));
        film.setFilmLength(resultSet.getInt("filmLength"));
        film.setLastSync(resultSet.getTimestamp("lastSync"));
        film.setIsBlocked(resultSet.getString("isBlocked"));
        return film;
    }
}