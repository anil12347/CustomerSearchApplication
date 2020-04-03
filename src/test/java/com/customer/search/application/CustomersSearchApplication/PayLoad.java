package com.customer.search.application.CustomersSearchApplication;

public class PayLoad {
	
	public static String authenticationUserName() {
		
		String authenticate="{\r\n" + 
				"\"username\":\"rupeek\",\r\n" + 
				"\"password\":\"password\"\r\n" + 
				"}";

		return authenticate;
		
	}

}
