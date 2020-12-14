package com.api.restassured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;
 
import com.api.restassured.files.payload;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ValidateRestAssuredAPI {

	//public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	@org.junit.Test
	public void restAPIValidation() {
		
		//AddPlace
		System.out.println("Start: AddPlace API via Post");
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println("postman response"+given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
					.body(payload.AddPlace()).when().post("maps/api/place/add/json")
					.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
					.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().toString());
	 		
		JsonPath js=new JsonPath(response); //for parsing Post response obtained as Json String
	     String placeId=js.get("place_id").toString();
		  System.out.println("PlaceId is "+placeId);
		  String statusMsg=js.get("status").toString();
		  System.out.println("Status is "+statusMsg);
		 
		System.out.println("End: AddPlace API via Post");
		
		//Update Place
		System.out.println("Start: UpdatePlace API via PUT");
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.header("Content-Type", "application/json").body("{\r\n" + 
				"\"place_id\":\""+placeId+"\",\r\n" + 
				"\"address\":\"70 winter walk, USA\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		System.out.println("End: UpdatePlace API via PUT");
		
		//GetPlace: 
		//Purpose: Validate Address actually got Updated
		String getResponseBody=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json")
				.then().log().all().assertThat().statusCode(200).extract().asString();
		System.out.println("Get API Response Body: "+getResponseBody);
		
		  JsonPath jp = new JsonPath(getResponseBody);  
		  String updatedAddress = jp.get("address").toString();
		  System.out.println("Updated Address"+updatedAddress);
		  
		  Map<String,String> headers = new HashMap<>();
		  headers.put("Content-Type","application/json");
		  //headers.put("Accept","*/*");
		  
		  given().queryParam("key", "qaclick123").headers(headers).body("{\r\n" + 
		  		"    \"place_id\":\""+placeId+"\" \r\n" + 
		  		"}\r\n" + "").when().post("maps/api/place/delete/json").then().log().all().assertThat().statusCode(200).body("status", equalTo("OK"));
		  	}
	}
