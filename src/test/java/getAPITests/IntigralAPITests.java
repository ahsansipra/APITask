package getAPITests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import datamodel.ErrorMain;
import datamodel.Root;
import io.restassured.response.Response;
import requests.GetAPIRequest;
import static org.hamcrest.collection.IsMapContaining.*;

public class IntigralAPITests extends GetAPIRequest {

	Response response;
	Root root;

	@Test
	public void verifySuccessStatusResponseCode() throws IOException {

		response = getAPIResponse(getProperties("apikey"));

		Assert.assertEquals(200, response.statusCode());

	}

	@Test
	public void verifyGetResponseKeys() throws IOException {

		given().spec(requestSpecification(getProperties("apikey"))).when().get(getProperties("resourceurl")).then()
				.assertThat().body("promotions[0]", hasKey("promotionId")).body("promotions[0]", hasKey("orderId"))
				.body("promotions[0]", hasKey("promoArea")).body("promotions[0]", hasKey("promoType"))
				.body("promotions[0]", hasKey("promoType"));

	}

	@Test
	public void verifyGetResponseLocalizedText() throws IOException {

		response = getAPIResponse(getProperties("apikey"));

		Assert.assertEquals(200, response.statusCode());
		root = response.as(Root.class);
		Assert.assertFalse(root.promotions.get(1).localizedTexts.ar.isEmpty()); // Arabic check
		Assert.assertFalse(root.promotions.get(1).localizedTexts.en.isEmpty()); // English check
		Assert.assertFalse(root.promotions.get(0).showPrice); // show price verification for boolean
		Assert.assertFalse(root.promotions.get(0).showText);
	}

	@Test
	public void verifyGetAPIResponseContents() throws IOException {

		response = getAPIResponse(getProperties("apikey"));
		root = response.as(Root.class);

		Assert.assertEquals("Cook with Aline", root.promotions.get(0).promotionId);
		Assert.assertEquals("movie", root.promotions.get(1).properties.get(0).programType);

	}

	@Test
	public void verifyFailResponseStatus() throws IOException {

		response = getAPIResponse(getProperties("apikey") + "123");
		Assert.assertEquals(403, response.statusCode());

	}

	@Test
	public void verifyFailResponseDetails() throws IOException {

		response = getAPIResponse(getProperties("apikey") + "123");
		ErrorMain errorDetails = response.as(ErrorMain.class);
		Assert.assertEquals(403, response.statusCode()); // status code verification

		Assert.assertEquals("invalid api key", errorDetails.getError().getMessage()); // error message
		Assert.assertEquals("8001", errorDetails.getError().getCode()); // code verification
		Assert.assertFalse(errorDetails.getError().getRequestId().isEmpty()); // requestID is not empty verification

	}

}
