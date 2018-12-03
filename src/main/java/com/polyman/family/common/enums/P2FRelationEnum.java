package com.polyman.family.common.enums;

public enum P2FRelationEnum {

	ADMIN("admin","管理"),
	CREATOR("creator","创建"),
	IS("is","从属");
	
	P2FRelationEnum(String value,String desc){
		 this.value=value;
		 this.desc=desc;
	}
	 
	private String value;
	 
	private String desc;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	 
	 
}
