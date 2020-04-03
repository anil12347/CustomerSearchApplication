package com.customer.search.application.CustomersSearchApplication;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods {
	public static JsonPath rawToJson(Response res) {
		String respon=res.asString();
		JsonPath j=new JsonPath(respon);
		return j;
	}
	
	public static String token() {
		RestAssured.baseURI="http://13.126.80.194:8080";
		Response res=(Response) given().contentType(ContentType.JSON).
		body(PayLoad.authenticationUserName()).when().
		post("/authenticate").
		then().assertThat().statusCode(200).and().extract().response();
		
		String responseString=res.asString();
		
		JsonPath js=ReusableMethods.rawToJson(res);
		String authenticate=js.get("token");
	
		return authenticate;
		
	}
	
	
	

}
