package responseModels;

import java.util.Set;

import movies.models.Genre;
import movies.models.Movie;

public class MovieResponseModel {
	private int id;

	private String title;

	private int votesCount;

	private double rating;

	private Set<Genre> genres;

	public MovieResponseModel(int id, String title, int votesCount, double rating, Set<Genre> genres) {
		this.setId(id);
		this.setTitle(title);
		this.setVotesCount(votesCount);
		this.setRating(rating);
		this.setGenres(genres);
	}

	public static MovieResponseModel FromModel(Movie movie) {
		return new MovieResponseModel(movie.getId(), movie.getTitle(), movie.getVotesCount(), movie.getRating(),
				movie.getGenres());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getVotesCount() {
		return votesCount;
	}

	public void setVotesCount(int votesCount) {
		this.votesCount = votesCount;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}
}
