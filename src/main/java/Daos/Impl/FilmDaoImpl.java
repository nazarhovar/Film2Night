package Daos.Impl;
import Connections.ConnectorToDB;
import Daos.FilmDao;
import Entities.Film;

import java.sql.*;
import java.util.Set;

public class FilmDaoImpl implements FilmDao {
    private final CountryDaoImpl countryDao;
    private final GenreDaoImpl genreDao;

    public FilmDaoImpl() {
        this.countryDao = new CountryDaoImpl();
        this.genreDao = new GenreDaoImpl();
    }

    public void addFilm(Film film) {
        try {
            Connection connection = ConnectorToDB.getConnection();

            int filmId = film.getKinopoiskId();
            Set<String> countries = film.getCountries();
            Set<String> genres = film.getGenres();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO film (kinopoiskId, nameOriginal, posterUrl, ratingKinopoisk, ratingKinopoiskVoteCount,webUrl,year,filmLength) VALUES (?,?,?,?,?,?,?,?)");
            statement.setString(1, String.valueOf(film.getKinopoiskId()));
            statement.setString(2,film.getNameOriginal());
            statement.setString(3,film.getPosterUrl());
            statement.setString(4, String.valueOf(film.getRatingKinopoisk()));
            statement.setString(5, String.valueOf(film.getRatingKinopoiskVoteCount()));
            statement.setString(6,film.getWebUrl());
            statement.setString(7, String.valueOf(film.getYear()));
            statement.setString(8, String.valueOf(film.getFilmLength()));;
            statement.executeUpdate();

            countryDao.linkFilmWithCountries(filmId, countries, connection);
            genreDao.linkFilmWithGenres(filmId, genres, connection);

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

    private Film resultSetToFilm(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        Film film = new Film();
        film.setKinopoiskId(resultSet.getInt("kinopoiskId"));
        film.setNameOriginal(resultSet.getString("nameOriginal"));
        film.setPosterUrl(resultSet.getString("posterUrl"));
        film.setRatingKinopoisk(resultSet.getFloat("ratingKinopoisk"));
        film.setRatingKinopoiskVoteCount(resultSet.getInt("ratingKinopoiskVoteCount"));
        film.setWebUrl(resultSet.getString("webUrl"));
        film.setYear(resultSet.getInt("year"));
        film.setFilmLength(resultSet.getInt("filmLength"));
        Set<String> countries = countryDao.getCountriesByFilmId(film.getKinopoiskId());
        film.setCountries(countries);
        Set<String> genres = genreDao.getGenresByFilmId(film.getKinopoiskId());
        film.setGenres(genres);
        return film;
    }
}