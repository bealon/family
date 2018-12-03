package com.polyman.family.bean.person;

import java.util.List;

import javax.validation.constraints.NotNull;


public class DeletePersonRequest {
	
	@NotNull(message ="删除人id不能为空")
	public String personNativeId;
	
	@NotNull(message ="删除家族id不能为空")
	public List<String> familyNativeIdList;

	public String getPersonNativeId() {
		return personNativeId;
	}

	public void setPersonNativeId(String personNativeId) {
		this.personNativeId = personNativeId;
	}

	public List<String> getFamilyNativeIdList() {
		return familyNativeIdList;
	}

	public void setFamilyNativeIdList(List<String> familyNativeIdList) {
		this.familyNativeIdList = familyNativeIdList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeletePersonRequest [personNativeId=");
		builder.append(personNativeId);
		builder.append(", familyNativeIdList=");
		builder.append(familyNativeIdList);
		builder.append("]");
		return builder.toString();
	}
		
}
