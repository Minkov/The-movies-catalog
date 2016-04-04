package movies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import movies.models.Genre;
import movies.utils.DataStorage;

@Service
public class InMemoryGenresService {

	public List<Genre> getAll() {
		return DataStorage.genres;
	}

	public Genre getGenreById(int id) {
		Genre genre = null;

		for (Genre currentGenre : DataStorage.genres) {
			if (currentGenre.getId() == id) {
				genre = currentGenre;
				break;
			}
		}
		return genre;
	}
}
