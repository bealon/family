package com.polyman.family.entity.neo4j.relationships;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.polyman.family.entity.neo4j.Family;
import com.polyman.family.entity.neo4j.Person;

/**
 * @author bealon
 */
@RelationshipEntity(type = "f2p")
public class F2PRelation {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@StartNode
	private Family family;
	
	@EndNode
	private Person user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}
			
}
