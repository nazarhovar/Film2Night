package Daos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
//
public interface CountryDao {
    Integer getCountryIdByName(String countryName, Connection connection) throws SQLException;
    void linkFilmWithCountries(int filmId, Set<String> countries, Connection connection) throws SQLException;
    Set<String> getCountriesByFilmId(int filmId) throws SQLException, ClassNotFoundException;
}
