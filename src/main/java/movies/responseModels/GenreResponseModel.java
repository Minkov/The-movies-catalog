package movies.responseModels;

import movies.models.Genre;

public class GenreResponseModel {
	private int id;
	private String name;

	public GenreResponseModel(int id, String name) {
		this.setId(id);
		this.setName(name);
	}

	public static GenreResponseModel fromModel(Genre model) {
		return new GenreResponseModel(model.getId(), model.getName());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
