package movies.models;

import java.util.HashSet;
import java.util.Set;

public class Genre {
	private int id;
	private String name;
	private Set<Movie> movies;

	public Genre() {
		this.movies = new HashSet<Movie>();
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

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			Genre other = (Genre) obj;
			return this.id == other.id;
		}

		catch (Exception ex) {
			return false;
		}
	}
}
