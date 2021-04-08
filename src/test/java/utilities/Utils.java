package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	static RequestSpecification request;

	public RequestSpecification requestSpecification(String apiKey) throws IOException {

		request = new RequestSpecBuilder().setBaseUri(getProperties("baseurl")).addQueryParam("apikey", apiKey).build();
		return request;

	}

	public String getProperties(String key) throws IOException {
		Properties prop = new Properties();
		InputStream fis = getClass().getClassLoader().getResourceAsStream("global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}

	public String getJsonString(Response res, String key) {
		String response = res.asString();
		JsonPath js = new JsonPath(response);
		return js.get(key).toString();
	}

}
