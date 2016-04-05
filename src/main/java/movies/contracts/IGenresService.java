package movies.contracts;

import java.util.List;

import movies.models.Genre;

public interface IGenresService {

	public List<Genre> getAll();

	public Genre getGenreById(int id);
}