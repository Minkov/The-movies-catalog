package movies.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import movies.contracts.IMoviesService;
import movies.models.Movie;
import responseModels.MovieResponseModel;

@RestController
@RequestMapping("/api")
public class MoviesController {
	// List<Movie> movies;
	private IMoviesService moviesService;

	@Autowired
	public MoviesController(IMoviesService service) throws Exception {
		this.moviesService = service;
	}

	// GET /api/movies?page=2 -> all movies
	// @RequestMapping("/movies")
	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public List<MovieResponseModel> getAll(Integer page, String pattern) throws Exception {
		return this.moviesService.search(pattern, page).stream().map(MovieResponseModel::FromModel)
				.collect(Collectors.toList());
	}

	// POST /api/movies -> create new movie
	@RequestMapping(value = "/movies", method = RequestMethod.POST)
	public Movie addMovie(@RequestBody Movie newMovie) throws Exception {
		return this.moviesService.add(newMovie.getTitle(), newMovie.getDescription());
	}

	// GET /api/movies/MOVIE_ID -> detailed info about movie
	@RequestMapping(value = "/movies/{movieId}", method = RequestMethod.GET)
	public Movie getById(@PathVariable(value = "movieId") int id) throws NoSuchRequestHandlingMethodException {
		return this.moviesService.getMovieById(id);
	}

	// PUT /api/movies/MOVIE_ID -> vote for movie

	@RequestMapping(value = "/movies/{movieId}", method = RequestMethod.PUT)
	public Movie voteForMovie(@PathVariable(value = "movieId") int id, @RequestBody int rating) throws Exception {
		return this.moviesService.voteForMovie(id, rating);
	}

	@RequestMapping(value = "/movies/{movieId}", method = RequestMethod.DELETE)
	public boolean deleteMovieById(@PathVariable(value = "movieId") int id) throws Exception {
		this.moviesService.deleteMovie(id);
		return true;
	}
}
