package sms.testbase;

import static io.restassured.RestAssured.given;

import java.awt.PageAttributes.MediaType;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import sms.config.HeaderConfig;
import sms.testdata.CreateSms;
import sms.util.Utility;

public class Demo extends Testbase{
	
	String tokenBaseUri = getPropertyValue("accessTokenBaseUri"); 
	String tokenPath = getPropertyValue("accessTokenPath");
	Response resp;
	String accessToken = null;
	CreateSms createSms;
	RequestSpecification rspec;
	Utility utility = new Utility();
	HeaderConfig headerConfig = new HeaderConfig();
	
       public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
	    	
			
			  Demo demo = new Demo(); 
			  demo.getResponse(); 
			  demo.getSMS();
			 
    	
		
		}
	
       public String getResponse() {
    	   
    	   String id = getPropertyValue("appId");
    	   System.out.println(id);
    	   String secret = getPropertyValue("appSecret");
    	   System.out.println(secret);
    				   
    	    String encodedData = encode(id, secret);
    	    System.out.println("encodeddata : " + encodedData);
    	   
			/*
			 * resp = given().baseUri("https://auth.routee.net") .header("Content-Type",
			 * "application/x-www-form-urlencoded") .header("Authorization",
			 * "Basic "+encodedData) .formParam("grant_type","client_credentials") .when()
			 * .post("/oauth/token") .then() .extract().response();
			 */
    	    resp  = given().baseUri("https://auth.routee.net")
			         .headers(headerConfig.headersWithAccessToken(encodedData))
			         .formParam("grant_type","client_credentials")
                     .when()
			         .post("/oauth/token")
			         .then()
			         .extract().response();
	
		System.out.println(resp.asString());
		
		JsonPath jp = new JsonPath(resp.asString());
		
		System.out.println(jp.getString("access_token"));
		
		return jp.getString("access_token");
		
       }
       
       
	     public String encode(String str1, String str2) 
	     {
	    	 byte[] b = Base64.getEncoder().encode((str1+":"+str2).getBytes());
	    	 String s = new String(b);
	    	 return s;
	     // return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
	    }
	     
	     public void getSMS()
	     {
				
	    	 createSms = new CreateSms();
	    	 createSms.setBody("A new game has been posted to the MindPuzzle. Check it out");
	    	 createSms.setTo("+919019134345"); 
	    	 createSms.setFrom("amdTelecom");
				 
	    	  
	    	  accessToken = getResponse();
	    	  System.out.println("accessToken - " + accessToken);
	    	  
	    	  
	    	  
				/*
				 * resp = given().baseUri("https://connect.routee.net/sms")
				 * .header("Authorization", "Bearer "+ accessToken) .header("Content-Type",
				 * "application/json") .body(createSms) .when() .post() .then()
				 * .extract().response();
				 */
	    	  
	    		 resp = given().baseUri("https://connect.routee.net/sms")
	 	    		    .headers(headerConfig.headerWithBearerToken(accessToken))
	 	    		    .body(createSms)
	 	    		    .when()
	 	    		    .post()
	 	    		    .then()
	 	    		    .extract().response();
	    	 
	    	 
	    	 System.out.println(resp.asString());
	    	 
             JsonPath jp = new JsonPath(resp.asString());
	    	 
	    	 System.out.println(jp.getString("developerMessage"));
	    	 
	    //	 System.out.println(utility.getStringValue(resp, "developerMessage"));
	    	 
	    	 
	     }
	     
	   public RequestSpecification reqSpecification()
	   {
		   createSms = new CreateSms();
		   createSms.setBody("A new game has been posted to the MindPuzzle. Check it out");
		   createSms.setTo("+919019134345");
		   createSms.setFrom("amdTelecom");
		   
		   rspec = new RequestSpecBuilder().setBody(createSms).setAccept("application/json").build();
		   return rspec;
	   }
	   
	   

}
