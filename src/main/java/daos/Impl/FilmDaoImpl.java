package daos.Impl;
import connections.DBConnector;
import daos.FilmDao;
import entities.Country;
import entities.Film;
import entities.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FilmDaoImpl implements FilmDao {
    private final CountryDaoImpl countryDao;
    private final GenreDaoImpl genreDao;
    DBConnector dbConnector = DBConnector.getInstance();

    private static final String INSERT = "INSERT INTO film (kinopoiskId, nameOriginal," +
            " posterUrl, ratingKinopoisk, ratingKinopoiskVoteCount,webUrl,year,filmLength," +
            "lastSync,isBlocked) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String GET_FILM = "SELECT * FROM film WHERE kinopoiskId = ?";
    private static final String SELECT_TWO_DAYS_FILMS = "SELECT kinopoiskId FROM film WHERE " +
            "lastSync <= DATE_SUB(NOW(), INTERVAL 2 DAY)";

    public FilmDaoImpl() throws SQLException, ClassNotFoundException {
        this.countryDao = new CountryDaoImpl();
        this.genreDao = new GenreDaoImpl();
    }

    public void addFilm(Film film) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {

            int filmId = film.getKinopoiskId();
            Set<Country> countries = film.getCountries();
            Set<Genre> genres = film.getGenres();

            statement.setString(1, String.valueOf(film.getKinopoiskId()));
            statement.setString(2, film.getNameOriginal());
            statement.setString(3, film.getPosterUrl());

            if (film.getRatingKinopoisk() != null) {
                statement.setString(4, String.valueOf(film.getRatingKinopoisk()));
            } else {
                statement.setNull(4, Types.FLOAT);
            }

            statement.setString(5, String.valueOf(film.getRatingKinopoiskVoteCount()));
            statement.setString(6, film.getWebUrl());
            statement.setString(7, String.valueOf(film.getYear()));
            statement.setString(8, String.valueOf(film.getFilmLength()));
            statement.setTimestamp(9, film.getLastSync());
            statement.setString(10, String.valueOf(film.getIsBlocked()));
            statement.executeUpdate();

            countryDao.linkFilmWithCountries(filmId, countries, connection);
            genreDao.linkFilmWithGenres(filmId, genres, connection);

            System.out.println("Film saved to database");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error adding film to database: " + e.getMessage());
        }
    }

    public Film getFilmById(int id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_FILM)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToFilm(resultSet);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error retrieving film from database: " + e.getMessage());
        }

        return null;
    }

    public List<Integer> getTwoDaysFilms() {
        List<Integer> twoDaysFilms = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_TWO_DAYS_FILMS)) {

            while (resultSet.next()) {
                int filmId = resultSet.getInt("kinopoiskId");
                twoDaysFilms.add(filmId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error retrieving two days films from database: " + e.getMessage());
        }

        return twoDaysFilms;
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

        Set<Country> countries = countryDao.getCountriesByFilmId(film.getKinopoiskId());
        film.setCountries(countries);
        Set<Genre> genres = genreDao.getGenresByFilmId(film.getKinopoiskId());
        film.setGenres(genres);

        film.setLastSync(resultSet.getTimestamp("lastSync"));
        film.setIsBlocked(resultSet.getString("isBlocked"));

        return film;
    }
}