package com.polyman.family.bean.person;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSON;


public class UpdatePersonRequest {
	
	@NotNull(message ="id不能为空")
	private String nativeId;
	
	private String nickName;
	
	private String headUrl;
	
	private String password;
	
	@NotNull(message ="名称不能为空")
	private String realName;
	
	private String phone;
	
	private Date birthday;
	
	@NotNull(message ="出生地址不能为空")
	private String bornPlace;
	
	private String idCard;
	
	private String desc;
	
	private String workPlace;
	
	private Integer searchDepth;
	
	private Integer  familySearchFlag;

	public String getNativeId() {
		return nativeId;
	}

	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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


	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public Integer getSearchDepth() {
		return searchDepth;
	}

	public void setSearchDepth(Integer searchDepth) {
		this.searchDepth = searchDepth;
	}

	public Integer getFamilySearchFlag() {
		return familySearchFlag;
	}

	public void setFamilySearchFlag(Integer familySearchFlag) {
		this.familySearchFlag = familySearchFlag;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdatePersonRequest [nativeId=");
		builder.append(nativeId);
		builder.append(", nickName=");
		builder.append(nickName);
		builder.append(", headUrl=");
		builder.append(headUrl);
		builder.append(", password=");
		builder.append(password);
		builder.append(", realName=");
		builder.append(realName);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", birthday=");
		builder.append(birthday);
		builder.append(", bornPlace=");
		builder.append(bornPlace);
		builder.append(", idCard=");
		builder.append(idCard);
		builder.append(", desc=");
		builder.append(desc);
		builder.append(", workPlace=");
		builder.append(workPlace);
		builder.append(", searchDepth=");
		builder.append(searchDepth);
		builder.append(", familySearchFlag=");
		builder.append(familySearchFlag);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		UpdatePersonRequest req=new UpdatePersonRequest();
		System.out.println(JSON.toJSONString(req));
	}
		
}
