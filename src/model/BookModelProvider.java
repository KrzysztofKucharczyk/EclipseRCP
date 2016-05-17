package model;

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
	
}
