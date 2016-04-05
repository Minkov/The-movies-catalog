package movies.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import movies.contracts.IMoviesService;
import movies.db.HibernateUtils;
import movies.models.Genre;
import movies.models.Movie;

@Service
public class DbMoviesService implements IMoviesService {

	@Override
	public List<Movie> search(String pattern, Integer page, Integer pageSize) throws Exception {
		Session session = HibernateUtils.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(Movie.class);

		List<Movie> movies = criteria.list();
		System.out.println("This");

		session.close();
		return movies;
	}

	@Override
	public Movie getMovieById(int id) throws NoSuchRequestHandlingMethodException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie voteForMovie(int id, int rating) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMovie(int id) throws NoSuchRequestHandlingMethodException {
		// TODO Auto-generated method stub

	}

	@Override
	public Movie add(String title, String description, String imgUrl, List<Genre> genres) throws Exception {
		Session session = HibernateUtils.getSessionFactory().openSession();

		Transaction transaction = session.getTransaction();
		transaction.begin();

		Movie movie = new Movie();
		movie.setTitle(title);
		movie.setDescription(description);
		movie.setImgUrl(imgUrl);
		movie.setRating(1);
		movie.setVotesCount(0);

		session.save(movie);
		transaction.commit();

		session.close();
		return movie;
	}

}
