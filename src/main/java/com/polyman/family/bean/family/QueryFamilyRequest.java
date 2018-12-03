 package com.polyman.family.bean.family;

public class QueryFamilyRequest {
	
    private String name="";
	
	private String birthland="";
	
	private Integer pageNo=1;
	
	private Integer pageSize=10;

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

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
