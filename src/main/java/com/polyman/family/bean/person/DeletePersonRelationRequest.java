 package com.polyman.family.bean.person;

import javax.validation.constraints.NotNull;


public class DeletePersonRelationRequest {
		
	@NotNull(message ="删除的关系人id")
	private String personNativeIdone;
	
	@NotNull(message ="添加的关系人id")
	private String personNativeIdtwo;
	
	
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




	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeletePersonRelationRequest [personNativeIdone=");
		builder.append(personNativeIdone);
		builder.append(", personNativeIdtwo=");
		builder.append(personNativeIdtwo);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
