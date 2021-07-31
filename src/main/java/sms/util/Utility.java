package sms.util;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import sms.config.HeaderConfig;
import sms.testbase.Testbase;
import sms.testdata.CreateSms;

public class Utility extends Testbase {

	JsonPath jp;
	Response resp;
	CreateSms createSms;
	File file;
	FileInputStream fis;
	HeaderConfig headerConfig;

	/**
	 * @author asahoo
	 * @param key of the value you want
	 * @return This method returns string value of key
	 * 
	 */
	public String getStringValue(String key) {
		jp = new JsonPath(resp.asString());
		System.out.println(resp.asString());
		return jp.getString(key);
	}

	public int getResponseCode() {
		return resp.getStatusCode();

	}

	/**
	 * @author asahoo
	 * @param appId
	 * @param appSecret
	 * @return This method returns Base64 encoded string
	 * @throws Exception
	 */
	public String encode(String appId, String appSecret) {
		byte[] byteData = Base64.getEncoder().encode((appId + ":" + appSecret).getBytes());
		return new String(byteData);

	}

	/**
	 * @author asahoo
	 * @param base64 encoded string value
	 * @return This method returns access code 
	 * @throws Exception
	 */
	public String getAccessToken(String encodedValue) {

		headerConfig = new HeaderConfig();
		if (encodedValue != null) {

			resp = given().baseUri("https://auth.routee.net").headers(headerConfig.headersWithAccessToken(encodedValue))
					.formParam("grant_type", "client_credentials").when().post("/oauth/token").then().extract()
					.response();
		} else {
			System.out.println("encodedData is null " + encodedValue);
		}

		JsonPath jp = new JsonPath(resp.asString());

		return jp.getString("access_token");

	}

	/**
	 * @author asahoo
	 * @param access token
	 * @return This method send sms and get the response back
	 * @throws Exception
	 */
	public Response sendSMSToUser(String accessToken) {
		headerConfig = new HeaderConfig();
		createSms = new CreateSms();
		createSms.setBody("A new game has been posted to the MindPuzzle. Check it out");
		createSms.setTo("+919019134345");
		createSms.setFrom("amdTelecom");

		resp = given().baseUri("https://connect.routee.net/sms")
				.headers(headerConfig.headerWithBearerToken(accessToken)).body(createSms).when().post().then().extract()
				.response();

		return resp;

	}

}
