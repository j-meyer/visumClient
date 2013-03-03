import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.JsonObject;

public class Couch {
	private int port;
	private String url;
	private String database;

	public Couch(String url, int port, String database) {
		this.url = url;
		this.port = port;
		this.database = database;
	}

	public void post(JsonObject object) throws IOException {
		HttpPost request = new HttpPost(url + ":" + port + "/" + database);
		HttpClient httpClient = new DefaultHttpClient();
		request.addHeader("content-type", "application/json");
		request.setEntity(new StringEntity(object.toString()));
		//HttpResponse response = 
		httpClient.execute(request);

	}

}
