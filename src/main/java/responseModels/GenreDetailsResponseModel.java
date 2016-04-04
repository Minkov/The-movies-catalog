package responseModels;

import java.util.Set;
import java.util.stream.Collectors;

import movies.models.Genre;

public class GenreDetailsResponseModel {

	private int id;
	private String name;
	private Set<MovieResponseModel> movies;

	public static GenreDetailsResponseModel fromModel(Genre model) {
		GenreDetailsResponseModel responseModel = new GenreDetailsResponseModel();
		responseModel.setId(model.getId());
		responseModel.setName(model.getName());
		Set<MovieResponseModel> movies = model.getMovies().stream().map(MovieResponseModel::FromModel)
				.collect(Collectors.toSet());

		responseModel.setMovies(movies);
		return responseModel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MovieResponseModel> getMovies() {
		return movies;
	}

	public void setMovies(Set<MovieResponseModel> movies) {
		this.movies = movies;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			GenreDetailsResponseModel other = (GenreDetailsResponseModel) obj;
			return this.id == other.id;
		} catch (Exception ex) {
			return false;
		}
	}
}
