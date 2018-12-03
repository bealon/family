package com.polyman.family.common.enums;

import com.polyman.common.base.bean.AppBizExcCodes;

public enum FamilyNetBizExcCodes implements AppBizExcCodes {
	
	;


	private String code;
	private String message;

	private FamilyNetBizExcCodes(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
