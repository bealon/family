 package com.polyman.family.bean.person;

import java.util.Date;

import javax.validation.constraints.NotNull;


public class QueryOneSelfRequest {
	
	@NotNull(message ="名称不能为空")
	private String realName;
	
	private String phone;
	
	private Date birthday;
	
	@NotNull(message ="出生地址不能为空")
	private String bornPlace;
	
	@NotNull(message ="不能为空")
	private String sex;
	
	private String idCard;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddPersonRequest [realName=");
		builder.append(realName);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", birthday=");
		builder.append(birthday);
		builder.append(", bornPlace=");
		builder.append(bornPlace);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", idCard=");
		builder.append(idCard);
		builder.append("]");
		return builder.toString();
	}
	
}
