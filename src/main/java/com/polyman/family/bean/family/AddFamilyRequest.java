package com.polyman.family.bean.family;

import javax.validation.constraints.NotNull;

public class AddFamilyRequest {
    
	@NotNull(message ="名称不能为空")
	private String name;
	
	@NotNull(message = "家族地址不能为空")
	private String birthland;
	
	private String desc;

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
	
   

}
