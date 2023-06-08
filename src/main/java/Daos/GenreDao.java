package Daos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface GenreDao {
    Integer getGenreIdByName(String genreName, Connection connection) throws SQLException;
    void linkFilmWithGenres(int filmId, Set<String> genres, Connection connection) throws SQLException;
    Set<String> getGenresByFilmId(int filmId) throws SQLException, ClassNotFoundException;
}
