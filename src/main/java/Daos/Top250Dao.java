package Daos;

import Entities.Top250;

import java.sql.SQLException;
import java.util.List;

public interface Top250Dao {
    void addTop250(Top250 topFilm) throws SQLException;
    List<Top250> getTop250() throws SQLException;
}
