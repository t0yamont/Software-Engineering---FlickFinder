package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;

public class PersonDAO {

    private final Connection connection;

    public PersonDAO() {
        Database database = Database.getInstance();
        connection = database.getConnection();
    }
    
    public List<Person> getAllPeople(String params) throws SQLException {
        String intial = (params == null) ? "50" : params;
        List<Person> people = new ArrayList<>();

        String query = "SELECT * FROM PEOPLE LIMIT " + intial;
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int birth = rs.getInt("birth");
                people.add(new Person(id, name, birth));
            }
        }
        return people;
    }

    public Person getPersonById(int id) throws SQLException {
        String query = "SELECT * FROM PEOPLE WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    int birth = rs.getInt("birth");
                    return new Person(id, name, birth);
                }
            }
        }
        return null; // Person not found
    }
    
        public List<Movie> getMoviesStarringPerson(int id) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT m.id, m.title, m.year\n" +
                        "FROM stars s\n" +
                       "JOIN movies m ON s.movie_id = m.id\n " +
                       "WHERE s.person_id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                    String title = rs.getString("title");
                    int year = rs.getInt("year");

                    movies.add(new Movie(id, title, year));
                }
                return movies;
            }
        }
    }


}
