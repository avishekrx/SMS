package sms.testdata;

public class CreateSms {

	private String body;
	private String to;
	private String from;
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	@Override
	public String toString() {
		return "Sms [body=" + body + ", to=" + to + ", from=" + from + "]";
	}
}
