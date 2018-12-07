package no.nrk.tomcat.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
	name=InfoEntity.selectInfoByKey,
	query="select i from InfoEntity i where i.key = :key")
public class InfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String selectInfoByKey = "selectInfoByKey";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	private long id;
	
	@Column(nullable=false, unique=true)
	private String key;
	
	@Column(nullable=false)
	private String value;

	public long getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static InfoEntity anInfoEntity(String key, String value) {
		InfoEntity entity = new InfoEntity();
		
		entity.key = key;
		entity.value = value;
		
		return entity;
	}
	
}
