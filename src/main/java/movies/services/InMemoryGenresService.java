package movies.services;

import java.util.List;

import movies.contracts.IGenresService;
import movies.models.Genre;
import movies.utils.DataStorage;

//@Service
public class InMemoryGenresService implements IGenresService {

	@Override
	public List<Genre> getAll() {
		return DataStorage.genres;
	}

	@Override
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
