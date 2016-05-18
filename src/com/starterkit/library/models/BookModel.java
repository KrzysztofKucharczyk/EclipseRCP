package com.starterkit.library.models;

public class BookModel {

	private Long id;
	private String title;
	private String authors;
	private String status;
	private String genre;
	private String year;

	public BookModel(Long id, String title, String authors, String status, String genre, String year) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.status = status;
		this.genre = genre;
		this.year = year;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
