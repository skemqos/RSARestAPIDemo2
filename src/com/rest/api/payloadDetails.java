package com.rest.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.api.restassured.files.payload;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class payloadDetails {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		setBaseUrl(null);
		System.out.println(createPlaceId());
		System.out.println(updatePlaceId());
		System.out.println(getPlaceId());
		System.out.println(deletePlaceId());
	}

	// create placeid
	public static String createPlaceId() {
		try {
			String response = given().log().all().queryParam("key", "qaclick123")
					.header("Content-Type", "application/json").body(payload.AddPlace()).when()
					.post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
					.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
			JsonPath js = new JsonPath(response); // for parsing Post response obtained as Json String
			String placeId = js.get("place_id").toString();
			return placeId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// update placeid
	public static boolean updatePlaceId() {
		return false;
	}

	// get placeId
	public static String getPlaceId() {
		return null;
	}

	// delete placeid
	public static boolean deletePlaceId() {
		return false;
	}

	// set baseUrl
	public static void setBaseUrl(String url) {
		if (url != null) {
			RestAssured.baseURI = url;
		} else {
			RestAssured.baseURI = "https://rahulshettyacademy.com";
		}
	}
}
