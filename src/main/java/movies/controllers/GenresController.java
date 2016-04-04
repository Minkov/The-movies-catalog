package movies.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import movies.models.Genre;
import movies.models.Movie;
import movies.services.InMemoryGenresService;

@RestController
@RequestMapping("/api")
public class GenresController {

	private InMemoryGenresService genresService;

	@Autowired
	public GenresController(InMemoryGenresService genresService) {
		this.genresService = genresService;
	}

	// GET /api/genres -> returns all genres
	@RequestMapping(value = "/genres", method = RequestMethod.GET)
	public List<Genre> getAllGenres() {
		return this.genresService.getAll();
	}

	// GET /api/genres/GENRE_ID -> return movies with this genre

	@RequestMapping(value = "/genres/{genreId}", method = RequestMethod.GET)
	public List<Movie> getMoviesForGenre(@PathVariable(value = "genreId") int id) {
		return this.genresService.getMoviesForGenre(id);
	}
}
