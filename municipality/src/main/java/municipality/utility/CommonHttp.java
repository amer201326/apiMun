package municipality.utility;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class CommonHttp {

	static DefaultHttpClient client = null;

	static public HttpReturnData postAPI(String url, String payload) throws IOException {

		if (client == null) {
			client = new DefaultHttpClient();
			CookieStore cookieStore = new BasicCookieStore();
			client.setCookieStore(cookieStore);
		}

		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json");// HTTP header
		String payLoad = payload; // HTTP payload/body
		HttpEntity entity = new ByteArrayEntity(payLoad.getBytes("UTF-8"));
		post.setEntity(entity);
		HttpResponse response = client.execute(post);// invocation
		response.getEntity().consumeContent();
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();

		HttpReturnData resData = new HttpReturnData();

		resData.setStatusCode(statusCode);
		HttpEntity resEntity = response.getEntity();
		if (resEntity.getContent().available() != 0) {
			String responseString = EntityUtils.toString(resEntity, "UTF-8");
			resData.setReturnContent(responseString);
		}

		return resData;
	}

	static public HttpReturnData getAPI(String url) throws ClientProtocolException, IOException {
		String responseString = "";

		if (client == null) {
			client = new DefaultHttpClient();
			CookieStore cookieStore = new BasicCookieStore();
			client.setCookieStore(cookieStore);
		}
		HttpGet get = new HttpGet(url);
		get.setHeader("Content-Type", "application/json");
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		responseString = EntityUtils.toString(entity, "UTF-8");

		HttpReturnData resData = new HttpReturnData();
		resData.setReturnContent(responseString);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		resData.setStatusCode(statusCode);

		return resData;

	}

	static public String getSessionCookie() {

		List<Cookie> cookies = client.getCookieStore().getCookies();
		if (!cookies.isEmpty()) {
			for (int i = 0; i < cookies.size(); i++) {
				if (cookies.get(i).getName().equals("JSESSIONID")) {
					return cookies.get(i).getValue();
				}
			}
		}
		return null;

	}

}
