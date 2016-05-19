package rest.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.starterkit.library.models.BookModel;

@SuppressWarnings("restriction")
public class RestServices {

	private final String universalURL = "http://localhost:8080/webstore/rbooks/";
	private final String instanceURL = "http://localhost:8080/webstore/rbook/";

	private HttpClient httpClient = HttpClientBuilder.create().build();

	public RestServices() {

	}

	public Collection<BookModel> getBooks() {
		HttpResponse response = sendGet(constructHttpGetRequest(universalURL));
		InputStream body = getResponseBody(response);
		checkStatusCode(response);
		System.out.println("GET  ::getBooks  :: == " + response.getStatusLine().getStatusCode());
		return convertJSONToBookList(convertRawResponseToJSON(body));
	}

	public Collection<BookModel> getBooks(String title, String authors) {
		if (!isNull(title, authors)) {
			String[] params = new String[2];
			params[0] = title;
			params[1] = authors;

			HttpResponse response = sendGet(constructHttpGetRequest(generateSearchURL(params)));
			InputStream body = getResponseBody(response);
			checkStatusCode(response);
			System.out.println("GET  ::getBooks  :: == " + response.getStatusLine().getStatusCode());

			return convertJSONToBookList(convertRawResponseToJSON(body));
		}
		return null;
	}

	public void saveBook(BookModel bookModel) {
		HttpPost request = new HttpPost(universalURL);
		request.addHeader("content-type", "application/json");
		request.setEntity(createJSONFromParams(bookModel));
		HttpResponse response = sendPost(request);
		checkStatusCode(response);
		System.out.println("POST ::saveBook  :: == " + response.getStatusLine().getStatusCode());
		try {
			EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public void updateBook(BookModel bookModel) {
		HttpPut request = new HttpPut(instanceURL + bookModel.getId());
		System.err.println(instanceURL + bookModel.getId());
		request.addHeader("content-type", "application/json");
		request.setEntity(createJSONFromParams(bookModel));
		try {
			System.err.println(request.getEntity().getContent());
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpResponse response = sendPut(request);
		checkStatusCode(response);
		System.out.println("PUT  ::updateBook :: == " + response.getStatusLine().getStatusCode());
		try {
			EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteBook(Long id) {
		HttpResponse response = sendDelete(constructHttpDeleteRequest(instanceURL + "/" + id));
		checkStatusCode(response);
		System.out.println("DEL  ::deleteBook:: == " + response.getStatusLine().getStatusCode());
		try {
			EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private HttpResponse sendGet(HttpGet request) {
		try {
			return httpClient.execute(request);
		} catch (IOException e) {

		}
		return null;
	}

	private HttpResponse sendPost(HttpPost request) {
		try {
			return httpClient.execute(request);
		} catch (Exception e) {

		}
		return null;
	}

	private HttpResponse sendPut(HttpPut request) {
		try {
			return httpClient.execute(request);
		} catch (Exception e) {

		}
		return null;
	}

	private HttpResponse sendDelete(HttpDelete request) {
		try {
			return httpClient.execute(request);
		} catch (IOException e) {

		}
		return null;
	}

	private StringEntity createJSONFromParams(BookModel bookModel) {

		JsonObject json = new JsonObject();
		json.add("id", bookModel.getId());
		json.add("title", bookModel.getTitle());
		json.add("authors", bookModel.getAuthors());
		json.add("status", bookModel.getStatus());
		json.add("genre", bookModel.getGenre());
		json.add("year", bookModel.getYear());
		System.out.println(json.toString());
		try {
			return new StringEntity(json.toString());
		} catch (UnsupportedEncodingException e) {

		}
		return null;
	}

	private List<BookModel> convertJSONToBookList(JsonArray jsonArray) {
		List<BookModel> books = new ArrayList<>();

		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject = (JsonObject) jsonArray.get(i);
			String[] preBook = new String[5];

			Long id = jsonObject.get("id").asLong();
			preBook[0] = jsonObject.get("title").toString();
			preBook[1] = jsonObject.get("authors").toString();
			preBook[2] = jsonObject.get("status").toString();
			preBook[3] = jsonObject.get("genre").toString();
			preBook[4] = jsonObject.get("year").toString();

			for (int j = 0; j < preBook.length; j++)
				preBook[j] = removeCharacters(preBook[j]);

			books.add(new BookModel(id, preBook[0], preBook[1], preBook[2], preBook[3], preBook[4]));
		}

		return books;
	}

	private String removeCharacters(String string) {
		return string.substring(1, string.length() - 1);
	}

	private JsonArray convertRawResponseToJSON(InputStream body) {

		try {
			return (JsonArray) JsonArray.readFrom(IOUtils.toString(body));
		} catch (IOException e) {

		}
		return null;
	}

	private void checkStatusCode(HttpResponse response) {
		// System.out.println(response.getStatusLine().getStatusCode());
	}

	private InputStream getResponseBody(HttpResponse response) {
		try {
			return response.getEntity().getContent();
		} catch (UnsupportedOperationException | IOException e) {

		}

		return null;
	}

	private HttpGet constructHttpGetRequest(String url) {
		HttpGet request = new HttpGet(url);
		request.addHeader("content-type", "application/json");
		return request;
	}

	private HttpDelete constructHttpDeleteRequest(String url) {
		HttpDelete request = new HttpDelete(url);
		request.addHeader("content-type", "application/json");
		return request;
	}

	private boolean isNull(String title, String authors) {
		return title == null || authors == null;
	}

	private String generateSearchURL(String[] searchTerms) {
		return "http://localhost:8080/webstore/rbooks/search?title=" + searchTerms[0] + "&authors=" + searchTerms[1];
	}

}
