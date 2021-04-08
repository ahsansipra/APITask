package requests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import io.restassured.response.Response;
import utilities.Utils;

public class GetAPIRequest extends Utils {

	Response response;

	public Response getAPIResponse(String apiKey) throws IOException {

		response = given().spec(requestSpecification(apiKey))
				  .when().get(getProperties("resourceurl"))
				  .then().extract()
				  .response();
		return response;
	}

}
