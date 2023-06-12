package Daos.Impl;

import Connections.ConnectorToDB;
import Daos.CountryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CountryDaoImpl implements CountryDao {

    public Integer getCountryIdByName(String countryName, Connection connection) throws SQLException {
        String query = "SELECT id FROM country WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, countryName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return null;
    }

    public void linkFilmWithCountries(int filmId, Set<String> countries, Connection connection) throws SQLException {
        if (countries != null && !countries.isEmpty()) {
            String query = "INSERT INTO film_country (film_id, country_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            for (String country : countries) {
                String[] countryNames = country.split(",");
                for (String countryName : countryNames) {
                    countryName = countryName.trim();
                    Integer countryId = getCountryIdByName(countryName, connection);
                    if (countryId != null) {
                        statement.setInt(1, filmId);
                        statement.setInt(2, countryId);
                        statement.executeUpdate();
                    }
                }
            }
        }
    }

    public Set<String> getCountriesByFilmId(int filmId) throws SQLException, ClassNotFoundException {
        Set<String> countries = new HashSet<>();
        Connection connection = ConnectorToDB.getConnection();
        String query = "SELECT country.name FROM country " +
                "INNER JOIN film_country ON film_country.country_id = country.id " +
                "WHERE film_country.film_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, filmId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String countryName = resultSet.getString("name");
            countries.add(countryName);
        }
        return countries;
    }
}
