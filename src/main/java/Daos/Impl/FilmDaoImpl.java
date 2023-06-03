package Daos.Impl;
import Connections.ConnectorToDB;
import Daos.FilmDao;
import Entities.Film;

import java.sql.*;



public class FilmDaoImpl implements FilmDao {

    public void addFilm(Film film) {
        try {
            Connection connection = ConnectorToDB.getConnection();

            int countryId = CountryDaoImpl.getCountryIdByName(String.valueOf(film.getCountries()));

            PreparedStatement statement = connection.prepareStatement("INSERT INTO film (kinopoiskId, nameOriginal, posterUrl, ratingKinopoisk, ratingKinopoiskVoteCount,webUrl,year,filmLength,country_id) VALUES (?,?,?,?,?,?,?,?,?)");
            statement.setString(1, String.valueOf(film.getKinopoiskId()));
            statement.setString(2,film.getNameOriginal());
            statement.setString(3,film.getPosterUrl());
            statement.setString(4, String.valueOf(film.getRatingKinopoisk()));
            statement.setString(5, String.valueOf(film.getRatingKinopoiskVoteCount()));
            statement.setString(6,film.getWebUrl());
            statement.setString(7, String.valueOf(film.getYear()));
            statement.setString(8, String.valueOf(film.getFilmLength()));
            statement.setInt(9, countryId);
//            statement.setString(10, String.valueOf(film.getGenre()));
//            statement.setString(9, String.valueOf(film.getLastSync()));
//            statement.setString(10,film.getIsBlocked());
            statement.executeUpdate();
            System.out.println("Film saved to database");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public Film getFilmById(int id) {
        Film film = null;
        try {
            Connection connection = ConnectorToDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM film WHERE kinopoiskId = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToFilm(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
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
//        film.setLastSync(resultSet.getTimestamp("lastSync"));
//        film.setIsBlocked(resultSet.getString("isBlocked"));
        return film;
    }
}