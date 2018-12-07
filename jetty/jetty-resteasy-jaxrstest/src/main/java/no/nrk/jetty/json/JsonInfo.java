package no.nrk.jetty.json;

public class JsonInfo {
	
	public String info;
	
	public static JsonInfo from(String info) {
		JsonInfo json = new JsonInfo();
		
		json.info = info;
		
		return json;
	}

}
