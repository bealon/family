package com.polyman.family.entity.neo4j;

import java.util.Date;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

@NodeEntity
public class Person {
	
	@Id
	@GeneratedValue
	private Long  id;
	
	@Index(unique=true)
	private String nativeId;
	
	private String nickName;
	
	private String headUrl;
	
	private String password;
	
	private String sex;
	
	private String idCard;
	
	private String realName;
	
	private String phone;
	
	@DateLong
	private Date birthday;
	
	private String bornPlace;
	
	private String desc;
	
	private String workPlace;
	
	private Integer searchDepth;
	
	//家庭搜索标志  0 家族外能搜到  1 家族内能搜到  2 无法搜到 
	private Integer  familySearchFlag;
	
	@DateLong
	private Date createDate;
	@DateLong
	private Date updateDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	
}
