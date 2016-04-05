package movies.responseModels;

import java.util.Set;
import java.util.stream.Collectors;

import movies.models.Movie;

public class MovieResponseModel {
	private static final int BRIEF_DESCRIPTION_LENGTH = 150;

	private int id;

	private String title;

	private int votesCount;

	private double rating;

	private String imgUrl;

	private String description;

	private Set<GenreResponseModel> genres;

	public MovieResponseModel(int id, String title, String description, String imgUrl, int votesCount, double rating,
			Set<GenreResponseModel> genres) {
		this.setId(id);
		this.setTitle(title);
		this.setDescription(description);
		this.setImgUrl(imgUrl);
		this.setVotesCount(votesCount);
		this.setRating(rating);
		this.setGenres(genres);
	}

	public static MovieResponseModel FromModel(Movie movie) {
		int id = movie.getId();
		String title = movie.getTitle();
		String description = movie.getDescription().substring(0, BRIEF_DESCRIPTION_LENGTH);
		String imgUrl = movie.getImgUrl();
		int votesCount = movie.getVotesCount();
		double rating = movie.getRating();
		Set<GenreResponseModel> genres = null;
		if (movie.getGenres() != null) {
			genres = movie.getGenres().stream().map(GenreResponseModel::fromModel).collect(Collectors.toSet());
		}
		return new MovieResponseModel(id, title, description, imgUrl, votesCount, rating, genres);
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

	public Set<GenreResponseModel> getGenres() {
		return genres;
	}

	public void setGenres(Set<GenreResponseModel> genres) {
		this.genres = genres;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			MovieResponseModel other = (MovieResponseModel) obj;
			return this.id == other.id;
		} catch (Exception ex) {
			return false;
		}
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
