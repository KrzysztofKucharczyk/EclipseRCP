package com.starterkit.library.models;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

import rest.manager.RestServices;

public enum BookModelProvider {
	INSTANCE;

	private RestServices restServices = new RestServices();

	private WritableList books = new WritableList(restServices.getBooks(), BookModel.class);

	private BookModelProvider() {
	}

	public void getBooksFromServer() {
		if (books != null)
			books.clear();
		List<BookModel> a = restServices.getBooks();
		books.addAll(a);
	}

	public void getBooksFromServer(String title, String authors) {
		books.clear();
		List<BookModel> a = restServices.getBooks(title, authors);
		books.addAll(a);
	}

	public void filterBooks(String title, String authors) {
		ArrayList<BookModel> searchTarget = new ArrayList<>();
		ArrayList<BookModel> titleSearchResults = new ArrayList<>();
		ArrayList<BookModel> authorsSearchResult = new ArrayList<>();
		ArrayList<BookModel> result = new ArrayList<>();

		for (int i = 0; i < books.size(); i++)
			searchTarget.add((BookModel) books.get(i));

		if (authors.equals("")) {
			for (BookModel book : searchTarget)
				if (book.getTitle().contains(title))
					result.add(book);
		}

		else if (title.equals("")) {
			for (BookModel book : searchTarget)
				if (book.getAuthors().contains(authors))
					result.add(book);
		}

		else {
			for (BookModel book : searchTarget) {
				if (book.getTitle().contains(title))
					titleSearchResults.add(book);
				if (book.getAuthors().contains(authors))
					authorsSearchResult.add(book);
			}

			for (BookModel book : titleSearchResults) {
				if (!authorsSearchResult.contains(book))
					result.add(book);
			}
			for (BookModel book : authorsSearchResult) {
				if (!titleSearchResults.contains(book))
					result.add(book);
			}

		}
		
		if (books != null)
			books.clear();
		books.addAll(result);

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

	public IObservableList getBooks() {
		return books;
	}

	public void setBooks(List<BookModel> books) {
		this.books = (WritableList) books;
	}
}
