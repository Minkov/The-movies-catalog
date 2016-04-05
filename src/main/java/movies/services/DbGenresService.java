package movies.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import movies.contracts.IGenresService;
import movies.db.HibernateUtils;
import movies.models.Genre;

@Service
public class DbGenresService implements IGenresService {

	@Override
	public List<Genre> getAll() {
		Session session = HibernateUtils.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(Genre.class);

		List<Genre> genres = criteria.list();

		session.close();
		return genres;
	}

	@Override
	public Genre getGenreById(int id) {
		return null;
	}

}
