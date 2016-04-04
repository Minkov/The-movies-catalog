package movies.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import movies.models.Genre;
import movies.services.InMemoryGenresService;
import responseModels.GenreDetailsResponseModel;
import responseModels.GenreResponseModel;

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
	public List<GenreResponseModel> getAllGenres() {
		return this.genresService.getAll().stream().map(GenreResponseModel::fromModel).collect(Collectors.toList());
	}

	// GET /api/genres/GENRE_ID -> return movies with this genre

	@RequestMapping(value = "/genres/{genreId}", method = RequestMethod.GET)
	public GenreDetailsResponseModel getGenreDetails(@PathVariable(value = "genreId") int id) {
		Genre genre = this.genresService.getGenreById(id);
		return GenreDetailsResponseModel.fromModel(genre);
	}
}
