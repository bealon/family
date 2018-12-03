package com.polyman.family.entity.neo4j;

import java.util.Date;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.neo4j.ogm.annotation.typeconversion.DateString;


@NodeEntity
public class Family {
	
	@Id
	@GeneratedValue
	private Long  id;
	
	@Index(unique=true)
	private String nativeId;
	
	private String name;
	
	private String birthland;
	
	private String desc;
	
	@DateLong
	private Date createDate;
	
	@DateLong
	private Date updateDate;
	
	public Long  getId() {
		return id;
	}

	public void setId(Long  id) {
		this.id = id;
	}
	
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
