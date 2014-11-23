package futurePayment.http;


public class Message {
	private String content;
	private String date;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String toString(){
		String s = "{content:" + content + ",date:" + date + "}";
		return s;
	}
}
