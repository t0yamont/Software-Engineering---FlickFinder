package com.flickfinder.dao;

import com.flickfinder.model.Movie;

public class MovieRating extends Movie {
    double rating;
    int votes;
   public MovieRating(int id, String title, int year, double rating, int votes) {
    super(id, title, year);
		this.rating = rating;
		this.votes = votes;
	}
    public double getRating() {
			return this.rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Integer getVote() {
			return this.votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
}
