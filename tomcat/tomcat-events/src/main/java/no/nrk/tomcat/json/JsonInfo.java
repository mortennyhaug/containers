package no.nrk.tomcat.json;

import no.nrk.tomcat.dtos.Info;

public class JsonInfo {
	
	public String info;
	
	public static JsonInfo fromInfo(Info info) {
		JsonInfo json = new JsonInfo();
		
		json.info = info.getInfo();
		
		return json;
	}
	
	public static Info toInfo(JsonInfo json) {
		return new Info(json.info);
	}

}
