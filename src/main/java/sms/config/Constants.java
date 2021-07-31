package sms.config;

public class Constants {
	
public static final class SmsConstants{
	
	    public static final String APP_ID="CustomAppID";
	    public static final String APP_SECRET="CustomSecret";
		
		//get access token
		public static final String ACCESS_TOKEN_BASEURI ="https://connect.routee.net";
		public static final String ACCESS_TOKEN_PATH="/oauth/token";
		
		//send sms
		public static final String SEND_SMS_BASERUI="https://connect.routee.net";
		public static final String SEND_SMS_PATH="/sms";
	}


}
