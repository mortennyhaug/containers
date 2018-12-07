package no.nrk.undertow.repositories;

public class SimpleInfoRepository implements InfoRepository {
	
	@Override
	public String getInfo() {
		return "undertow + resteasy and weld";
	}

}
