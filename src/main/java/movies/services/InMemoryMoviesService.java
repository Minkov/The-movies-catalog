package movies.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import movies.contracts.IMoviesService;
import movies.models.Genre;
import movies.models.Movie;
import movies.utils.DataStorage;
import movies.utils.IdGenerator;

@Service
public class InMemoryMoviesService implements IMoviesService {
	private final static int PAGE_SIZE = 10;
	private IdGenerator idGenerator;

	public InMemoryMoviesService() {
		this.idGenerator = new IdGenerator();
	}

	@Override
	public List<Movie> search(String pattern, Integer page) {
		page = (page == null || page < 1) ? 1 : page;

		final String thePattern = (pattern == null ? "" : pattern).toLowerCase();

		System.out.printf("Pattern: %s%n", pattern);
		return DataStorage.movies.stream().filter(movie -> movie.getTitle().toLowerCase().contains(thePattern))
				.collect(Collectors.toList()).subList((page - 1) * PAGE_SIZE, page * PAGE_SIZE);
	}

	@Override
	public Movie add(String title, String description) throws Exception {
		return this.add(title, description, new ArrayList<Genre>());
	}

	@Override
	public Movie add(String title, String description, List<Genre> genres) throws Exception {
		Movie movie = new Movie();
		movie.setId(this.idGenerator.getNextId());
		movie.setRating(1);
		movie.setVotesCount(0);
		movie.setTitle(title);
		movie.setDescription(description);

		Set<Genre> theGenres = genres.stream().map(g -> {
			for (Genre currentGenre : DataStorage.genres) {
				boolean result = currentGenre.getName().toLowerCase().equals(g.getName().toLowerCase());
				if (result) {
					return currentGenre;
				}
			}
			DataStorage.genres.add(g);
			return g;
		}).collect(Collectors.toSet());

		movie.setGenres(new HashSet<Genre>(theGenres));
		DataStorage.movies.add(movie);
		return movie;
	}

	@Override
	public Movie getMovieById(int id) throws NoSuchRequestHandlingMethodException {
		for (Movie currentMovie : DataStorage.movies) {
			if (currentMovie.getId() == id) {
				return currentMovie;
			}
		}

		throw new NoSuchRequestHandlingMethodException("Not found such movie", Movie.class);
	}

	@Override
	public Movie voteForMovie(int id, int rating) throws Exception {
		Movie movie = this.getMovieById(id);

		double oldRatingsSum = movie.getRating() * movie.getVotesCount();

		int newVotesCount = movie.getVotesCount() + 1;

		double newRating = (oldRatingsSum + rating) / newVotesCount;

		movie.setRating(newRating);

		movie.setVotesCount(newVotesCount);
		return movie;
	}

	@Override
	public void deleteMovie(int id) throws NoSuchRequestHandlingMethodException {
		int index = -1;
		for (int i = 0, len = DataStorage.movies.size(); i < len; i++) {
			Movie movie = DataStorage.movies.get(i);
			if (movie.getId() == id) {
				index = i;
				break;
			}
		}

		if (index < 0) {
			throw new NoSuchRequestHandlingMethodException("Not found such movie", Movie.class);
		}

		DataStorage.movies.remove(index);
	}
}
