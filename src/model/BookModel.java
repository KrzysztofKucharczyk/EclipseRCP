package model;

public class BookModel {

	private String title;
	private String authors;
	private String status;
	private String genre;
	private String year;

	public BookModel(String title, String authors, String status, String genre, String year) {
		this.title = title;
		this.authors = authors;
		this.status = status;
		this.genre = genre;
		this.year = year;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
