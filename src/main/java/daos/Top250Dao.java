package daos;

import entities.Top250;

import java.sql.SQLException;
import java.util.List;

public interface Top250Dao {
    List<Top250> getTop250() throws SQLException;
    void addTop250(List<Top250> topFilms) throws SQLException;
}
