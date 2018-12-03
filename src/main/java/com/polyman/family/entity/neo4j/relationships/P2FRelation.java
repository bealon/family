package com.polyman.family.entity.neo4j.relationships;

import java.util.List;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.polyman.family.entity.neo4j.Family;
import com.polyman.family.entity.neo4j.Person;

/**
 * admin   管理
 * creator 创建 
 * is      从属
 * @author bealon
 *
 */
@RelationshipEntity(type = "p2f")
public class P2FRelation {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private List<String> name;
	
	@StartNode
	private Person user;

	@EndNode
	private Family family;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
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
