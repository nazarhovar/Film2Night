package daos;

import entities.Genre;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GenreDao {
    Map<String, Integer> getGenreIdByName(List<String> genreNames, Connection connection) throws SQLException;
    void linkFilmWithGenres(int filmId, Set<Genre> genres, Connection connection) throws SQLException;
    Set<Genre> getGenresByFilmId(int filmId) throws SQLException, ClassNotFoundException;
}
