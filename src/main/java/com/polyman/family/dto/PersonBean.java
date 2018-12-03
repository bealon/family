package com.polyman.family.dto;

import java.util.Date;

public class PersonBean {
			
	private String headUrl;
	
	private String sex;
	
	private String realName;
	
	private Date birthday;	
		
	private String bornPlace;
	
	private String desc;
	
	FamilyBean family;

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBornPlace() {
		return bornPlace;
	}

	public void setBornPlace(String bornPlace) {
		this.bornPlace = bornPlace;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public FamilyBean getFamily() {
		return family;
	}

	public void setFamily(FamilyBean family) {
		this.family = family;
	}

}
