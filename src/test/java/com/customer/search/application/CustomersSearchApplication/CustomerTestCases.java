package com.customer.search.application.CustomersSearchApplication;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;



public class CustomerTestCases {
	/*
	 * Testcase for how to get Token
	 */
	@Test
	public void authenication() {
		System.out.println("************Testcase 1 starts***************");
		RestAssured.baseURI=ResourceAndBaseUrl.host;
		Response res=(Response) given().contentType(ContentType.JSON).
		body(PayLoad.authenticationUserName()).when().
		post("/authenticate").
		then().assertThat().statusCode(200).and().extract().response();
		
		String responseString=res.asString();
		System.out.println(responseString);
		JsonPath js=ReusableMethods.rawToJson(res);
		String authenticate=js.get("token");
		System.out.println(authenticate);
		System.out.println("************Testcase 1 Ends***************");
		
	}
	
	/*
	 * Using token get the all records
	 */
	@Test
	public void recordsOfAllCutomers() {
		System.out.println("************Testcase 2 starts***************");
		RestAssured.baseURI=ResourceAndBaseUrl.host;
		Response res=(Response) given().auth().oauth2(ReusableMethods.token()).
		when().
		get(ResourceAndBaseUrl.user).
		then().assertThat().statusCode(200).and().extract().response();
		
		String responseString=res.asString();
		
		JsonPath js=ReusableMethods.rawToJson(res);
		int count=js.get("size()");
		System.out.println("Size"+count);
		for(int i=0;i<count;i++) {
			String first_name=js.getString("first_name["+i+"]");
			String last_name=js.getString("last_name["+i+"]");
			String career=js.getString("career["+i+"]");
			String phone = js.getString("phone["+i+"]");
			System.out.println("Position of : " + i);
			System.out.print("Firstname : "+first_name +"\n");
			System.out.print("LastName : "+last_name+"\n");
			System.out.print("Career : "+career+"\n");
			System.out.print("Phone : "+phone+"\n");
			
		}
		
		System.out.println("************Testcase 2 ends***************");
		
	}
	/*
	 * Using phone get all user details
	 */
	@Test(dataProvider="PhoneNumber")
	public void userWithPhoneNumber(String phoneNumber) {
		System.out.println("************Testcase 3 starts***************");
		RestAssured.baseURI=ResourceAndBaseUrl.host; 
		Response res=(Response) given().auth().oauth2(ReusableMethods.token()).
		when().
		get(ResourceAndBaseUrl.user+"/"+phoneNumber).
		then().assertThat().statusCode(200).and().extract().response();
		
		JsonPath js=ReusableMethods.rawToJson(res);
		
		String first_name=js.getString("first_name");
		String last_name=js.getString("last_name");
		String phone = js.getString("phone");
		System.out.print("Firstname : "+first_name +"\n");
		System.out.print("LastName : "+last_name+"\n");
		System.out.print("Phone : "+phone+"\n");
		System.out.println("************Testcase 3 ends***************");
		
	}
	
	
	@DataProvider(name="PhoneNumber")
	public Object[] getphoneNumber() {
	
		return new Object[] {"8037602400","9972939567","9995879555"};
	}
	
	

}
