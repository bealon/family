 package com.polyman.family.bean.person;

import java.util.List;

import javax.validation.constraints.NotNull;


public class AddPersonRelationRequest {
		
	@NotNull(message ="添加的关系人id")
	private String personNativeIdone;
	
	@NotNull(message ="添加的关系人id")
	private String personNativeIdtwo;
	
	@NotNull(message ="添加的关系人家庭id")
	private List<String> familyNativeIds;
	
	@NotNull(message ="人员关系")
	private String p2pRelation;

	public String getPersonNativeIdone() {
		return personNativeIdone;
	}

	public void setPersonNativeIdone(String personNativeIdone) {
		this.personNativeIdone = personNativeIdone;
	}

	public String getPersonNativeIdtwo() {
		return personNativeIdtwo;
	}

	public void setPersonNativeIdtwo(String personNativeIdtwo) {
		this.personNativeIdtwo = personNativeIdtwo;
	}

	public List<String> getFamilyNativeIds() {
		return familyNativeIds;
	}

	public void setFamilyNativeIds(List<String> familyNativeIds) {
		this.familyNativeIds = familyNativeIds;
	}

	public String getP2pRelation() {
		return p2pRelation;
	}

	public void setP2pRelation(String p2pRelation) {
		this.p2pRelation = p2pRelation;
	}

	
	
}
