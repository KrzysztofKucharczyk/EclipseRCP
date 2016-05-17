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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import model.BookModel;

@SuppressWarnings("restriction")
public class RestServices {

	private final String universalURL = "http://localhost:8080/webstore/rbooks/";

	private HttpClient httpClient = HttpClientBuilder.create().build();

	public RestServices() {

	}

	public Collection<BookModel> getBooks() {
		HttpResponse response = sendGet(constructHttpGetRequest(universalURL));
		InputStream body = getResponseBody(response);
		checkStatusCode(response);

		return convertJSONToBookList(convertRawResponseToJSON(body));
	}

	public Collection<BookModel> getBooks(String title, String authors) {
		HttpResponse response = sendGet(
				constructHttpGetRequest(generateSearchURL(generateFinalSearchTerms(title, authors))));
		InputStream body = getResponseBody(response);
		checkStatusCode(response);

		return convertJSONToBookList(convertRawResponseToJSON(body));
	}

	public void saveBook(JsonValue json) {

		HttpPost request = new HttpPost(universalURL);
		request.addHeader("content-type", "application/json");
		request.setEntity(createJSONFromParams(json));
		HttpResponse response = sendPost(request);
		checkStatusCode(response);
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

	private StringEntity createJSONFromParams(JsonValue json) {
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

			preBook[0] = jsonObject.get("title").toString();
			preBook[1] = jsonObject.get("authors").toString();
			preBook[2] = jsonObject.get("status").toString();
			preBook[3] = jsonObject.get("genre").toString();
			preBook[4] = jsonObject.get("year").toString();

			for (int j = 0; j < preBook.length; j++)
				preBook[j] = removeCharacters(preBook[j]);

			books.add(new BookModel(preBook[0], preBook[1], preBook[2], preBook[3], preBook[4]));
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

	private String[] generateFinalSearchTerms(String title, String authors) {

		if (title == null)
			title = "";

		if (authors == null)
			authors = "";

		final String[] result = new String[2];
		result[0] = new String(title);
		result[1] = new String(authors);

		return result;
	}

	private String generateSearchURL(String[] searchTerms) {
		return "http://localhost:8080/webstore/rbooks/search?title=" + searchTerms[0] + "&authors=" + searchTerms[1];
	}
}
