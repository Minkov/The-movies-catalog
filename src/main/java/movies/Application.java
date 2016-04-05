package movies;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import movies.models.Genre;
import movies.models.Movie;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		// Session session = HibernateUtils.getSessionFactory().openSession();
		// seedGenres(session);
		// seedMovies(session);
		// session.close();

		// seed();
	}

	private static void seedMovies(Session session) {

		Transaction transaction = session.getTransaction();

		transaction.begin();

		int count = 100;

		List<Genre> genres = session.createCriteria(Genre.class).list();

		for (int i = 0; i < count; i++) {
			String title = "Movie #" + (i + 1);
			Criteria criteria = session.createCriteria(Movie.class);
			criteria.add(Restrictions.eq("title", title));

			Object dbMovie = criteria.uniqueResult();
			if (dbMovie != null) {
				continue;
			}
			System.out.printf("Movie %s is not in db%n", title);

			Movie movie = new Movie();

			movie.setTitle(title);
			String description = "Description for movie " + movie;
			for (int j = 0; j < 3; j++) {
				description += description;
			}
			movie.setDescription(description);
			movie.setImgUrl(
					"http://static2.gamespot.com/uploads/scale_large/104/1049837/2891179-batman-arkham_knight-review_nologo_20150618.jpg");
			try {
				movie.setRating(1);
			} catch (Exception ex) {

			}
			movie.setVotesCount(2);

			Set<Genre> movieGenres = new HashSet<Genre>();

			for (int k = 0; k < 3; k++) {
				int index = (k + i) % genres.size();
				movieGenres.add(genres.get(index));
			}

			movie.setGenres(movieGenres);

			session.save(movie);
		}

		transaction.commit();
	}

	private static void seedGenres(Session session) {
		String[] genreNames = { "Thriller", "Sci-Fi", "Action", "Comedy", "Romance", "Drama", "Adventure", "History",
				"Documentary", "Horror", "Animated", "Anime" };

		Transaction transaction = session.getTransaction();

		transaction.begin();

		for (String genreName : genreNames) {

			Criteria criteria = session.createCriteria(Genre.class);
			criteria.add(Restrictions.eq("name", genreName));

			Object dbGenre = criteria.uniqueResult();
			if (dbGenre != null) {
				continue;
			}

			Genre newGenre = new Genre();
			newGenre.setName(genreName);
			session.save(newGenre);
		}

		transaction.commit();
	}
}
