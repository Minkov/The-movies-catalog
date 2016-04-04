package movies.contracts;

import java.util.List;

import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import movies.models.Genre;
import movies.models.Movie;

public interface IMoviesService {

	public List<Movie> search(String pattern, Integer page);

	public Movie add(String title, String description) throws Exception;

	public Movie add(String title, String description, List<Genre> movieGenres) throws Exception;

	public Movie getMovieById(int id) throws NoSuchRequestHandlingMethodException;

	public Movie voteForMovie(int id, int rating) throws Exception;

	public void deleteMovie(int id) throws NoSuchRequestHandlingMethodException;

}
