package movies.models;

import java.util.HashSet;
import java.util.Set;

public class Movie {
	private int id;

	private String title;

	private String description;

	private String imgUrl;

	private int votesCount;

	private double rating;

	private Set<Genre> genres;

	public Movie() {
		this.genres = new HashSet<Genre>();
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

	public void setRating(double rating) throws Exception {
		if (rating < 1 || rating > 5) {
			throw new Exception();
		}

		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			Movie other = (Movie) obj;
			return this.id == other.id;
		} catch (Exception ex) {
			return false;
		}
	}
}
