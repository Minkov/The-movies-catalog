package movies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import movies.contracts.IMoviesService;
import movies.models.Genre;
import movies.services.InMemoryMoviesService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		seed();
	}

	private static void seed() {
		IMoviesService service = new InMemoryMoviesService();
		int count = 100;

		List<Genre> genres = getGenres();

		saveMovies(service, count, genres);
	}

	private static void saveMovies(IMoviesService service, int count, List<Genre> genres) {
		for (int i = 0; i < count; i++) {
			String title = "Movie #" + (i + 1);
			String description = "Я Ю ѝ ж ъ ь ѝDescription for movie #" + (i + 1);
			String imgUrl = "http://www.telegraph.co.uk/content/dam/film/Batmanvsuperman/batmanvsuperman-xlarge.jpg";

			for (int k = 0; k < 3; k++) {
				description += description;
			}

			List<Genre> movieGenres = new ArrayList<Genre>();

			int genresPerMovie = (int) (Math.random() * 4) + 2;

			for (int j = 0; j < genresPerMovie; j++) {
				movieGenres.add(genres.get((i + j) % genres.size()));
			}

			try {
				service.add(title, description, imgUrl, movieGenres);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static List<Genre> getGenres() {
		String[] genreNames = { "Thriller", "Sci-Fi", "Action", "Comedy", "Romance", "Drama", "Adventure", "History",
				"Documentary" };

		List<Genre> genres = new ArrayList<Genre>();
		for (int i = 0; i < genreNames.length; i++) {
			Genre g = new Genre();
			g.setId(i + 1);
			g.setName(genreNames[i]);
			genres.add(g);
		}
		return genres;
	}
}
