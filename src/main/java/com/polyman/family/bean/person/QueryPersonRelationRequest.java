 package com.polyman.family.bean.person;

import javax.validation.constraints.NotNull;


public class QueryPersonRelationRequest {
		
	@NotNull(message ="姓名不能为空")
	private String realNameOne;
	
	@NotNull(message ="姓名不能为空")
	private String realNameTwo;

	public String getRealNameOne() {
		return realNameOne;
	}

	public void setRealNameOne(String realNameOne) {
		this.realNameOne = realNameOne;
	}

	public String getRealNameTwo() {
		return realNameTwo;
	}

	public void setRealNameTwo(String realNameTwo) {
		this.realNameTwo = realNameTwo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryPersonRelationRequest [realNameOne=");
		builder.append(realNameOne);
		builder.append(", realNameTwo=");
		builder.append(realNameTwo);
		builder.append("]");
		return builder.toString();
	}
	
}
