package com.polyman.family.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="person")

public class PersonConfig {

	private Integer searchDepth;
		
	//家庭搜索标志  0 家族外能搜到  1 家族内能搜到  2 无法搜到 
	private Integer  familySearchFlag;

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

	
	
}
