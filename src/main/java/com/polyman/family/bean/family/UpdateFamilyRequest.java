package com.polyman.family.bean.family;

import javax.validation.constraints.NotNull;

public class UpdateFamilyRequest {
	
	@NotNull(message ="id不能为空")
	private String nativeId;
	
	private String name;
	
	private String birthland;
	
	private String desc;

	public String getNativeId() {
		return nativeId;
	}

	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthland() {
		return birthland;
	}

	public void setBirthland(String birthland) {
		this.birthland = birthland;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateFamilyRequest [nativeId=");
		builder.append(nativeId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", birthland=");
		builder.append(birthland);
		builder.append(", desc=");
		builder.append(desc);
		builder.append("]");
		return builder.toString();
	}
}
