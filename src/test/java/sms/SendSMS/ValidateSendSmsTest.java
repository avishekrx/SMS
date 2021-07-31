package sms.SendSMS;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;
import sms.config.Constants.SmsConstants;
import sms.testbase.Testbase;
import sms.testdata.CreateSms;
import sms.testdata.GetSmsResponse;
import sms.util.Utility;

public class ValidateSendSmsTest extends Testbase{
	String id = SmsConstants.APP_ID;
	String secret = SmsConstants.APP_SECRET;
	String access_token;
	String baseEncode ;
	Utility utility = new Utility();
	//GetSmsResponse getSmsResponse = new GetSmsResponse();
	CreateSms createSms = new CreateSms();
	Response response;
	
	@BeforeSuite
	public void getBearerToken()
	{
		baseEncode = utility.encode(id, secret);
		access_token = utility.getAccessToken(baseEncode);
	
	}
	
	@BeforeTest
	public void sendSms()
	{
		utility.sendSMSToUser(access_token);
   	 
	}
	
	@Test
	public void validateSmsResponseStatusCode()
	{
		response = utility.sendSMSToUser(access_token);
		Assert.assertEquals(response.getStatusCode(),400, "Failed - Expected error is 400");
	//	System.out.println("400 Not found");
	}
	
	
	@Test
	public void validateSmsResponseMessage()
	{
		response = utility.sendSMSToUser(access_token);
	//	System.out.println("Validate Insufficient balance");
	//	String actualMessage = utility.getStringValue("developerMessage");
	//	System.out.println(actual);
		Assert.assertEquals(utility.getStringValue("developerMessage"), "Insufficient Balance","Failed - Expected developerMessage is 'Insufficient balance'");
	}
	
	

}
