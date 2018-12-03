package com.polyman.family.bean.family;

import javax.validation.constraints.NotNull;

public class JoinFamilyRequest {
	
	@NotNull(message ="添加人id不能为空")
	public String personNativeId;
	
	@NotNull(message ="添加家族id不能为空")
	public String familyNativeId;

	public String getPersonNativeId() {
		return personNativeId;
	}

	public void setPersonNativeId(String personNativeId) {
		this.personNativeId = personNativeId;
	}

	public String getFamilyNativeId() {
		return familyNativeId;
	}

	public void setFamilyNativeId(String familyNativeId) {
		this.familyNativeId = familyNativeId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JoinFamilyRequest [personNativeId=");
		builder.append(personNativeId);
		builder.append(", familyNativeId=");
		builder.append(familyNativeId);
		builder.append("]");
		return builder.toString();
	}
	
	
}
