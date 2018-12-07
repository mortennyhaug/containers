package no.nrk.undertow.repositories;

import javax.enterprise.inject.Alternative;

@Alternative
public class FakeInfoRepository implements InfoRepository {

	@Override
	public String getInfo() {
		return "fake info repository";
	}

}
