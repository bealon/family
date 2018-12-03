 package com.polyman.family.bean.person;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;


public class AddPersonRequest {
	
	@NotNull(message ="名称不能为空")
	private String realName;
	
	private String phone;
	
	private Date birthday;
	
	@NotNull(message ="出生地址不能为空")
	private String bornPlace;
	
	@NotNull(message ="不能为空")
	private String sex;
	
	private String idCard;
	
	private String personNativeId;
	
	@NotNull(message ="添加的关系人家庭id")
	private List<String> familyNativeIds;
	
	private String p2pRelation;

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
	
	public String getPersonNativeId() {
		return personNativeId;
	}

	public void setPersonNativeId(String personNativeId) {
		this.personNativeId = personNativeId;
	}

	public String getP2pRelation() {
		return p2pRelation;
	}

	public void setP2pRelation(String p2pRelation) {
		this.p2pRelation = p2pRelation;
	}

	
	public List<String> getFamilyNativeIds() {
		return familyNativeIds;
	}

	public void setFamilyNativeIds(List<String> familyNativeIds) {
		this.familyNativeIds = familyNativeIds;
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
		builder.append(", personNativeId=");
		builder.append(personNativeId);
		builder.append(", familyNativeIds=");
		builder.append(familyNativeIds);
		builder.append(", p2pRelation=");
		builder.append(p2pRelation);
		builder.append("]");
		return builder.toString();
	}
	
}
