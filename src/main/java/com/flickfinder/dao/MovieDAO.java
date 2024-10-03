package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;

/**
 * The Data Access Object for the Movie table.
 * 
 * This class is responsible for getting data from the Movies table in the
 * database.
 * 
 */
public class MovieDAO {

	/**
	 * The connection to the database.
	 */
	private final Connection connection;

	/**
	 * Constructs a SQLiteMovieDAO object and gets the database connection.
	 * 
	 */
	public MovieDAO() {
		Database database = Database.getInstance();
		connection = database.getConnection();
	}

	/**
	 * Returns a list of all movies in the database.
	 * 
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */

	 public List<Movie> getAllMovies() throws SQLException {
		return this.getAllMovies();
	 }

	public List<Movie> getAllMovies(String params) throws SQLException {
		String first = params;
		if (first == null){
			first = "50";
		}
		List<Movie> movies = new ArrayList<>();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM movies LIMIT ?");
		statement.setInt(1, Integer.parseInt(first));
		ResultSet rs = statement.executeQuery();
		
		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}

		return movies;
	}
	

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param id the id of the movie
	 * @return the movie with the specified id
	 * @throws SQLException if a database error occurs
	 */
	public Movie getMovieById(int id) throws SQLException {

		String statement = "select * from movies where id = ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			return new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"));
		}
		

		return null;

	}
	
	public List<Person> getPeopleByMovieId(int id) throws SQLException {
	    List<Person> people = new ArrayList<>();
	    String query = "SELECT p.id, p.name, p.birth\n " +
	                   "FROM stars s\n " +
	                   "JOIN people p ON s.person_id = p.id\n " +
	                   "WHERE s.movie_id = ?;";
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, id);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                id = rs.getInt("id");
	                String name = rs.getString("name");
	                int birth = rs.getInt("birth");
	                people.add(new Person(id, name, birth));
	            }
	        }
	    }
	    return people;
	}

	public List<MovieRating> getRatingsByYear(int year, String param, String minVotes) throws SQLException {
		String initialLimit = (param == null) ? "50" : param;
		String initialVotes = (minVotes == null) ? "1000" : minVotes;
		List<MovieRating> movies = new ArrayList<>();
		String statement = "SELECT m.id, m.title, m.year, r.votes, r.rating\n " +
		"FROM movies m\n " +
		"JOIN ratings r ON m.id = r.movie_id\n " +
		"WHERE m.year = ? AND r.votes > ?\n " +
		"ORDER BY r.rating DESC LIMIT " + initialLimit;
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, year);
		ps.setInt(2, Integer.parseInt(initialVotes));
		//ps.setInt(3,Integer.parseInt(initialLimit));
		//ps.setInt(1, year);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			System.out.println(rs.getInt("votes"));
			movies.add(new MovieRating(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getDouble("rating"), rs.getInt("votes")));
		}
		return movies;
	}

}
