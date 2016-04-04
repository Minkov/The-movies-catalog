package movies.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import movies.models.Genre;
import movies.models.Movie;
import movies.utils.DataStorage;

@Service
public class InMemoryGenresService {

	public List<Genre> getAll() {
		return DataStorage.genres;
	}

	public List<Movie> getMoviesForGenre(int id) {
		Genre genre = null;

		for (Genre currentGenre : DataStorage.genres) {
			if (currentGenre.getId() == id) {
				genre = currentGenre;
				break;
			}
		}

		final Genre theGenre = genre;

		return DataStorage.movies.stream().filter(movie -> movie.getGenres().contains(theGenre))
				.collect(Collectors.toList());
	}
}
