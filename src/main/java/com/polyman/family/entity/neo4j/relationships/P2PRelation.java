package com.polyman.family.entity.neo4j.relationships;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.polyman.family.entity.neo4j.Person;

@RelationshipEntity(type = "p2p")
public class P2PRelation {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@StartNode
	private Person user;

	@EndNode
	private Person relative;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public Person getRelative() {
		return relative;
	}

	public void setRelative(Person relative) {
		this.relative = relative;
	}
	
}
