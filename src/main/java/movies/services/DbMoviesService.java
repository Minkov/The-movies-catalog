package movies.services;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import movies.contracts.IMoviesService;
import movies.db.HibernateUtils;
import movies.models.Genre;
import movies.models.Movie;

@Service
public class DbMoviesService implements IMoviesService {

	private static final int DEFAULT_PAGE_SIZE = 10;

	@Override
	public List<Movie> search(String pattern, Integer page, Integer pageSize) throws Exception {
		page = (page == null) ? 1 : page;
		pageSize = (pageSize == null) ? DEFAULT_PAGE_SIZE : page;

		Session session = HibernateUtils.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(Movie.class);

		// criteria.setFirstResult((page - 1) * pageSize);
		// criteria.setMaxResults(pageSize);

		List<Movie> movies = criteria.list();
		System.out.println(movies.size());
		System.out.println("This");

		session.close();
		return movies;
	}

	@Override
	public Movie getMovieById(int id) throws NoSuchObjectException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Movie movie = getMovieById(id, session);
		session.close();
		return movie;
	}

	private Movie getMovieById(int id, Session session) throws NoSuchObjectException {
		Criteria criteria = session.createCriteria(Movie.class);

		criteria.add(Restrictions.idEq(id));

		Object result = criteria.uniqueResult();
		if (result == null) {
			throw new NoSuchObjectException("No movie with id: " + id);
		}

		Movie movie = (Movie) result;
		return movie;
	}

	@Override
	public Movie voteForMovie(int id, int rating) throws Exception {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Movie movie = this.getMovieById(id, session);

		double oldRatingSum = movie.getRating() * movie.getVotesCount();

		int newVotesCount = movie.getVotesCount() + 1;

		double newRating = (oldRatingSum + rating) / newVotesCount;

		Transaction transaction = session.getTransaction();
		transaction.begin();

		movie.setVotesCount(newVotesCount);
		movie.setRating(newRating);

		session.update(movie);
		transaction.commit();

		return movie;
	}

	@Override
	public void deleteMovie(int id) throws NoSuchObjectException {
		Session session = HibernateUtils.getSessionFactory().openSession();

		Movie movie = this.getMovieById(id, session);

		Transaction transaction = session.getTransaction();
		transaction.begin();

		session.delete(movie);

		transaction.commit();

		session.close();
	}

	@Override
	public Movie add(String title, String description, String imgUrl, List<String> genres) throws Exception {
		Session session = HibernateUtils.getSessionFactory().openSession();

		Transaction transaction = session.getTransaction();
		transaction.begin();

		Movie movie = new Movie();
		movie.setTitle(title);
		movie.setDescription(description);
		movie.setImgUrl(imgUrl);
		movie.setRating(1);
		movie.setVotesCount(0);
		Set<Genre> dbGenres = genres.stream().map(genreName -> {
			Criteria criteria = session.createCriteria(Genre.class);
			criteria.add(Restrictions.eq("name", genreName));
			Object result = criteria.uniqueResult();
			if (result != null) {
				return (Genre) result;
			}
			Genre genre = new Genre();
			genre.setName(genreName);
			session.save(genre);
			return genre;
		}).collect(Collectors.toSet());

		movie.setGenres(dbGenres);

		session.save(movie);
		transaction.commit();

		session.close();
		return movie;
	}

}
