package daos.Impl;

import connections.DBConnector;
import daos.CountryDao;
import entities.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CountryDaoImpl implements CountryDao {

    private static final String GET_ID = "SELECT id,name FROM country WHERE name IN (?)";
    private static final String INSERT = "INSERT INTO film_country (film_id, country_id) VALUES (?, ?)";
    private static final String GET_COUNTRIES = "SELECT country.name FROM country " +
            "INNER JOIN film_country ON film_country.country_id = country.id " +
            "WHERE film_country.film_id = ?";

    DBConnector dbConnector = DBConnector.getInstance();

    public CountryDaoImpl() throws SQLException, ClassNotFoundException {
    }

    public Map<String, Integer> getCountryIdByName(List<String> countryNames, Connection connection) throws SQLException {
        Map<String, Integer> countryIdMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ID)) {
            String countryNameString = String.join(",", countryNames);
            statement.setString(1, countryNameString);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    countryIdMap.put(resultSet.getString("name"), resultSet.getInt("id"));
                }
            }
        }

        return countryIdMap;
    }

    public void linkFilmWithCountries(int filmId, Set<Country> countries, Connection connection) throws SQLException {
        if (countries == null || countries.isEmpty()) {
            return;
        }

        PreparedStatement statement = connection.prepareStatement(INSERT);

        countries.stream()
                .map(Country::getName)
                .forEach(countryName -> {
                    try {
                        Map<String, Integer> countryIdMap = getCountryIdByName(Collections.singletonList(countryName),
                                connection);
                        Integer countryId = countryIdMap.get(countryName);
                        if (countryId != null) {
                            statement.setInt(1, filmId);
                            statement.setInt(2, countryId);
                            statement.addBatch();
                        }
                    } catch (SQLException e) {
                        System.out.println("Error linking film with countries: " + e.getMessage());
                    }
                });

        statement.executeBatch();
    }

    public Set<Country> getCountriesByFilmId(int filmId) throws SQLException, ClassNotFoundException {
        Set<Country> countries = new HashSet<>();
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_COUNTRIES)) {
            statement.setInt(1, filmId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Country country = new Country();
                    country.setName(resultSet.getString("name"));
                    countries.add(country);
                }
            }
        }

        return countries;
    }
}
