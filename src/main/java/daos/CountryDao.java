package daos;

import entities.Country;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CountryDao {
    Map<String, Integer> getCountryIdByName(List<String> countryNames, Connection connection) throws SQLException;
    void linkFilmWithCountries(int filmId, Set<Country> countries, Connection connection) throws SQLException;
    Set<Country> getCountriesByFilmId(int filmId) throws SQLException, ClassNotFoundException;
}
