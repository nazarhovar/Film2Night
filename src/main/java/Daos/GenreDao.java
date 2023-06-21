package Daos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GenreDao {
    Map<String, Integer> getGenreIdByName(List<String> genreNames, Connection connection) throws SQLException;
    void linkFilmWithGenres(int filmId, Set<String> genres, Connection connection) throws SQLException;
    Set<String> getGenresByFilmId(int filmId) throws SQLException, ClassNotFoundException;
}
