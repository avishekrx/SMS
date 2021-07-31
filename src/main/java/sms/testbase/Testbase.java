package sms.testbase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Testbase {
	
	Properties prop;
	Response resp;
	FileInputStream fis;
	String file = System.getProperty("user.dir")+"/src/main/java/sms/config/config.properties";
	
	public Testbase()
	{
		prop = new Properties();
		try {
			fis = new FileInputStream(file);
			prop.load(fis);
			
		} catch (FileNotFoundException e) {
			//use Log
			e.printStackTrace();
		} catch (IOException e) {
			// use log
			e.printStackTrace();
		}
	}
	
	
	
	public String getPropertyValue(String key)
	{
		return prop.getProperty(key);
		
	}
	
	 public String encode(String appId, String appSecret) 
     {
    	 byte[] byteString = Base64.getEncoder().encode((appId+":"+appSecret).getBytes());
    	// String encodedString = new String(byteString);
    	 return new String(byteString);
     
    }
	 
	 public String getAccessToken(String encodedValue) {
	   	  
	  	    if(encodedValue!=null)
	  	    {
	  		resp  = given().baseUri("https://auth.routee.net")
			     .header("Content-Type", "application/x-www-form-urlencoded")
	             .header("Authorization", "Basic "+encodedValue)
	             .formParam("grant_type","client_credentials") 
	             .when()
			     .post("/oauth/token")
			         .then()
			         .extract().response();
	  	    }else {
	  	    	System.out.println("encodedValue is null " + encodedValue);
	  	    }
		
	//		System.out.println(resp.asString());
			
			JsonPath jp = new JsonPath(resp.asString());
			
	//		System.out.println(jp.getString("access_token"));
			
			assertNull(jp.getString("access_token"));
			
			return jp.getString("access_token");
			
	     }

}
