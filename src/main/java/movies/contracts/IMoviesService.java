package movies.contracts;

import java.rmi.NoSuchObjectException;
import java.util.List;

import movies.models.Movie;

public interface IMoviesService {

	public List<Movie> search(String pattern, Integer page, Integer pageSize) throws Exception;

	public Movie getMovieById(int id) throws NoSuchObjectException;

	public Movie voteForMovie(int id, int rating) throws Exception;

	public void deleteMovie(int id) throws NoSuchObjectException;

	public Movie add(String title, String description, String imgUrl, List<String> genres) throws Exception;

}
