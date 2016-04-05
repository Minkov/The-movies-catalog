package movies.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import movies.contracts.IMoviesService;
import movies.db.HibernateUtils;
import movies.models.Genre;
import movies.models.Movie;
import movies.utils.DataStorage;
import movies.utils.IdGenerator;

//@Service
public class InMemoryMoviesService implements IMoviesService {
	private final static int DEFAULT_PAGE_SIZE = 10;
	private IdGenerator idGenerator;

	public InMemoryMoviesService() {
		this.idGenerator = new IdGenerator();
		System.out.println("In Movies service");
	}

	@Override
	public List<Movie> search(String pattern, Integer page, Integer pageSize) throws Exception {
		Session session = HibernateUtils.getSessionFactory().openSession();

		pageSize = (pageSize == null || pageSize < 1) ? DEFAULT_PAGE_SIZE : pageSize;
		page = (page == null || page < 1) ? 1 : page;

		final String thePattern = (pattern == null ? "" : pattern).toLowerCase();

		session.close();
		return DataStorage.movies.stream().filter(movie -> movie.getTitle().toLowerCase().contains(thePattern))
				.collect(Collectors.toList()).subList((page - 1) * pageSize, page * pageSize);
	}

	@Override
	public Movie add(String title, String description, String imgUrl, List<Genre> genres) throws Exception {
		Movie movie = new Movie();
		movie.setId(this.idGenerator.getNextId());
		movie.setRating(1);
		movie.setVotesCount(0);
		movie.setTitle(title);
		movie.setImgUrl(imgUrl);
		movie.setDescription(description);

		Set<Genre> theGenres = genres.stream().map(g -> {
			for (Genre currentGenre : DataStorage.genres) {
				boolean result = currentGenre.getName().toLowerCase().equals(g.getName().toLowerCase());
				if (result) {
					currentGenre.getMovies().add(movie);
					return currentGenre;
				}
			}

			DataStorage.genres.add(g);
			g.getMovies().add(movie);
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
