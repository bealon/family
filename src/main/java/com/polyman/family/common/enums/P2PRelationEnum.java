package com.polyman.family.common.enums;

public enum P2PRelationEnum {

	FATHER("father","父亲"),
	MOTHER("mother","母亲"),
	HUSBAND("husband","丈夫"),
	WIFE("wife","妻子"),
	BROTHER("brother","兄"),
	YBROTHER("ybrother","弟"),
	SISTER("sister","姐"),
	YSISTER("ysister","妹"),
	BAS("bas","兄弟姐妹"),
	SON("son","儿子"),
	DAUGHTER("daughter","女儿");
	
	P2PRelationEnum(String value,String desc){
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
	
	public static P2PRelationEnum getByValue(String value){
		P2PRelationEnum [] p2PRelationEnums=P2PRelationEnum.values();
		for(P2PRelationEnum temp:p2PRelationEnums){
			if(temp.getValue().equals(value)){
				return temp;
			}
		}
		return null;
	}
	 
}
