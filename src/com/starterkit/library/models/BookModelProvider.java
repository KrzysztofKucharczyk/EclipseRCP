package com.starterkit.library.models;

import java.util.Collection;

import rest.manager.RestServices;

public enum BookModelProvider {
	INSTANCE;
	
	private RestServices restServices = new RestServices();
	
	private BookModelProvider() {
		
	}
	
	public Collection<BookModel> getBooks() {
		return restServices.getBooks();
	}
	
	public Collection<BookModel> getBooks(String title, String authors) {
		return restServices.getBooks(title, authors);
	}
	
	public void saveBook(BookModel bookModel) {
		restServices.saveBook(bookModel);
	}
	
	public void updateBook(BookModel bookModel) {
		restServices.updateBook(bookModel);
	}
	
	public void deleteBook(Long id) {
		restServices.deleteBook(id);
	}
}
