package sms.config;

import java.util.HashMap;
import java.util.Map;

public class HeaderConfig {
	
    public HeaderConfig() {
		
	}
	
	public Map<String, String> headerWithBearerToken(String accessToken){
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer "+ accessToken);
		headers.put("Content-Type", "application/json");

		return headers;
		
	}
	
	
	public Map<String, String> headersWithAccessToken(String encodedData){
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Authorization", "Basic "+encodedData);
		
		return headers;
		
	}

}
